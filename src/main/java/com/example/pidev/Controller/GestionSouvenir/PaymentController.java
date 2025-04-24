package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Command;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.PaymentConfirmationRequest;
import com.example.pidev.exception.InsufficientStockException;
import com.example.pidev.exception.NotFoundException;
import com.example.pidev.repository.GestionSouvenir.CommandRepository;
import com.example.pidev.repository.GestionSouvenir.DiscountRepository;
import com.example.pidev.service.GestionSouvenir.iCommandService;
import com.example.pidev.service.GestionSouvenir.iPanelService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Autowired
    private iCommandService commandService;

    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private iPanelService panelService;

    @Autowired
    private DiscountRepository discountRepository;

    @PostConstruct
    public void init() {
        try {
            if (stripeSecretKey == null || !stripeSecretKey.startsWith("sk_test")) {
                throw new IllegalArgumentException("Configuration Stripe invalide");
            }
            Stripe.apiKey = stripeSecretKey;
            Stripe.setMaxNetworkRetries(2);
            logger.info("Stripe initialisé avec la clé: {}", stripeSecretKey.substring(0, 12) + "...");
        } catch (Exception e) {
            logger.error("ÉCHEC initialisation Stripe: {}", e.getMessage());
        }
    }


    @PostMapping("/create-pending-command")
    public ResponseEntity<?> createPendingCommand(@RequestBody Panel panel) {
        try {
            // Validation du panier
            if (panel.getCommandLines() == null || panel.getCommandLines().isEmpty()) {
                throw new IllegalArgumentException("Le panier ne peut pas être vide");
            }
            panel.updateTotal();
            // Création de la commande en statut PENDING
            Command command = commandService.createCommandFromPanel(panel);

            return ResponseEntity.ok(Map.of(
                    "commandId", command.getId(),
                    "Total", command.getTotal(),
                    "status", "PENDING"
            ));

        } catch (Exception ex) {
            logger.warn("Validation échouée: {}", ex.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/confirm-payment/{commandId}")
    public ResponseEntity<?> confirmPayment(
            @PathVariable Long commandId,
            @RequestBody PaymentConfirmationRequest request, HttpSession session) {

        try {
            String returnUrl = "http://localhost:4200/payment-success/" + commandId;
            // 1. Validation de la commande
            Command command = commandRepository.findById(commandId)
                    .orElseThrow(() -> new NotFoundException("Commande non trouvée"));

            // Conversion sécurisée depuis un double
            BigDecimal total = BigDecimal.valueOf(command.getTotal()) // <-- Correction ici
                    .setScale(2, RoundingMode.HALF_UP);

            // Validation
            if (total.scale() > 2) {
                throw new IllegalArgumentException("Format monétaire invalide");
            }

            if (total.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Montant invalide");
            }

            long amountCents = total.multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.UNNECESSARY)
                    .longValueExact();

            // 4. Configuration Stripe
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountCents)
                    .setCurrency("eur")
                    .setPaymentMethod(request.getPaymentMethodId())
                    .setConfirm(true)
                    .setReturnUrl(returnUrl) // <-- Ajouté ici
                    .putMetadata("command_id", commandId.toString())
                    .build();


            // 5. Clé d'idempotence
            RequestOptions requestOptions = RequestOptions.builder()
                    .setIdempotencyKey("confirm-" + commandId + "-" + Instant.now().toEpochMilli())
                    .build();

            // 6. Création du PaymentIntent
            PaymentIntent intent = PaymentIntent.create(params, requestOptions);

            // 7. Gestion du statut
            switch (intent.getStatus()) {
                case "succeeded":
                    commandService.finalizeCommand(commandId);
                    logger.info("Paiement réussi pour la commande {}", commandId);
                    // Vider le panier
                    panelService.clearCart(session);
                    return ResponseEntity.ok()
                            .body(Map.of(
                                    "status", "CONFIRMED",
                                    "paymentIntentId", intent.getId(),
                                    "amount", amountCents,
                                    "currency", "EUR"
                            ));
//                panelService.clearCart(Panel);

                case "requires_action":
                    logger.info("3D Secure requis pour la commande {}", commandId);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(Map.of(
                                    "status", "REQUIRES_ACTION",
                                    "clientSecret", intent.getClientSecret()
                            ));

                default:
                    commandService.cancelCommand(commandId);
                    logger.error("Échec du paiement pour la commande {} : {}", commandId, intent.getLastPaymentError());
                    return ResponseEntity.badRequest()
                            .body(Map.of(
                                    "status", "FAILED",
                                    "error", intent.getLastPaymentError() != null ?
                                            intent.getLastPaymentError().getMessage() : "Erreur inconnue"
                            ));
            }

        } catch (NotFoundException ex) {
            logger.error("Commande introuvable : {}", commandId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));

        } catch (StripeException ex) {
            logger.error("Erreur Stripe : {} - {}", ex.getCode(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(Map.of(
                            "error", "Échec du traitement du paiement",
                            "code", ex.getCode(),
                            "detail", ex.getStripeError() != null ?
                                    ex.getStripeError().getMessage() : ex.getMessage()
                    ));

        } catch (IllegalArgumentException | IllegalStateException ex) {
            logger.warn("Validation échouée : {}", ex.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ex.getMessage()));

        } catch (InsufficientStockException ex) {
            logger.error("Stock insuffisant : {}", ex.getMessage());
            commandService.cancelCommand(commandId);
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", ex.getMessage()));

        } catch (Exception ex) {
            logger.error("Erreur interne : {}", ex.getMessage(), ex);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur interne du serveur"));
        }
    }

}