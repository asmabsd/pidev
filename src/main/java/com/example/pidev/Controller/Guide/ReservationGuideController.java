package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.entity.GUIDE.ratingrequest;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.GUIDE.GuideReservationRepository;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.service.GUIDE.IReservationGuideService;
import com.example.pidev.service.GUIDE.WhatsAppService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ReservationGuide")
@CrossOrigin(origins = "http://localhost:4200/")

public class ReservationGuideController {
    @Autowired
    IReservationGuideService ReservationGuideService;

@Autowired
GuideReservationRepository ReservationGuideRepository;
    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private UserRepository userRepository;
    String message = "new reservation sent to you know check your account  ";
   @PostMapping("/addReservationGuide")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ReservationGuide addReservationGuide(@RequestBody ReservationGuide reservationGuide) {

        // Set the status before saving
        reservationGuide.setStatus("pending");

        // ✅ Save reservation FIRST to generate ID
        ReservationGuide savedReservation = ReservationGuideService.addReservationGuide(reservationGuide);

        Guide guide = guideRepository.findById(savedReservation.getGuide().getId()).orElse(null);

        if (guide != null && guide.getPhone() != null) {
            // ✅ Now the ID is available
            String message = "Good morning " + guide.getName() +
                    ", you have a new reservation at " + savedReservation.getDateHour() +
                    ". Would you like to accept it?\n\n" +
                    "Please choose one of the options below:\n" +
                    "✅ Accept: http://localhost:8089/ReservationGuide/webhookTwilio?action=accept&reservationId=" + savedReservation.getIdReservation() + "\n" +
                    "❌ Reject: http://localhost:8089/ReservationGuide/webhookTwilio?action=reject&reservationId=" + savedReservation.getIdReservation();

            try {
                whatsAppService.sendWhatsAppMessage(guide.getPhone(), message);
            } catch (Exception e) {
                System.err.println("Error sending WhatsApp message: " + e.getMessage());
            }
        } else {
            System.err.println("Guide's phone number is not available or guide not found.");
        }






        return savedReservation;
    }

    /*@PostMapping("/addReservationGuide")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ReservationGuide addReservationGuide(@RequestBody  ReservationGuide reservationGuide, String userMessage) {
        reservationGuide.setStatus("pending");

        // ✅ Save reservation FIRST to generate ID
        ReservationGuide savedReservation = ReservationGuideService.addReservationGuide(reservationGuide);

        Guide guide = guideRepository.findById(savedReservation.getGuide().getId()).orElse(null);
        ReservationGuide pending = ReservationGuideRepository.findTopByGuideAndStatusOrderByDateHourDesc(guide, "pending");
        if (pending == null) {
            return null;
        }

        if (userMessage.contains("accept")) {
            pending.setStatus("accepted");
            ReservationGuideRepository.saveAndFlush(pending);
        } else if (userMessage.contains("reject")) {
            pending.setStatus("rejected");
            ReservationGuideRepository.saveAndFlush(pending);
        }

        System.out.println("Updated reservation status: " + pending.getStatus());
        return pending;
    }*/


    @PostMapping("/handleReply")
    public ResponseEntity<ReservationGuide> handleIncomingMessage(@RequestParam("From") String from,
                                                                  @RequestParam("Body") String body) {
        String phone = from.replace("whatsapp:", "").trim();
        String userMessage = body.trim().toLowerCase();

        Guide guide = guideRepository.findByPhone(phone);
        if (guide == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ReservationGuide pending = ReservationGuideRepository.findTopByGuideAndStatusOrderByDateHourDesc(guide, "pending");
        if (pending == null) {
            return ResponseEntity.ok().build(); // No pending reservations
        }

        if (userMessage.contains("accept")) {
            pending.setStatus("accepted");
        } else if (userMessage.contains("reject")) {
            pending.setStatus("rejected");
        }

        ReservationGuideRepository.saveAndFlush(pending);
        System.out.println("📩 Message reçu de WhatsApp: " + from + " - " + body);

        System.out.println("Updated reservation status: " + pending.getStatus());
        return ResponseEntity.ok(pending);


    }





    @PutMapping("/updateReservationGuide/{id}")
    @CrossOrigin(origins = "http://localhost:4200/")
    ReservationGuide updateReservationGuide(@PathVariable int id,@RequestBody ReservationGuide ReservationGuide){
        return  ReservationGuideService.updateReservationGuide(ReservationGuide);
    }
    @GetMapping("/viewReservationGuide")
    List<ReservationGuide> afficherReservationGuide(){
        return  ReservationGuideService.getAllReservationGuide();


    }
  /*  @Transactional
    @PostMapping("/handleReply")
    public ResponseEntity<ReservationGuide> handleIncomingMessage(@RequestParam("From") String from,
                                                                  @RequestParam("Body") String body) {

        String phone = from.replace("whatsapp:", "").trim();
        String userMessage = body.trim().toLowerCase();

        Guide guide = guideRepository.findByPhone(phone);
        if (guide == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ReservationGuide pending = ReservationGuideRepository.findTopByGuideAndStatusOrderByDateHourDesc(guide, "pending");

        if (pending == null) {
            return ResponseEntity.ok().build(); // No pending
        }

        if (userMessage.contains("accept")) {
            pending.setStatus("accepted");
            ReservationGuideRepository.saveAndFlush(pending);
        } else if (userMessage.contains("reject")) {
            pending.setStatus("rejected");
            ReservationGuideRepository.saveAndFlush(pending);
        }

        System.out.println("Updated reservation status: " + pending.getStatus());
        return ResponseEntity.ok(pending);
    }

*/

    @DeleteMapping("/deleteReservationGuide/{idReservationGuide}")
    public void deleteReservationGuide(@PathVariable int idReservationGuide) {
        ReservationGuideService.deleteReservationGuide(idReservationGuide);
    }


    @GetMapping("/getOne/{idReservationGuide}")

    public ReservationGuide getReservationGuide(@PathVariable int idReservationGuide) {
        return ReservationGuideService.getReservationGuide(idReservationGuide);
    }
    // Vérifiez que l'URL correspond à votre API Spring
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @RequestParam String status) {

        // Implémentez la logique de mise à jour ici
        ReservationGuideService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/byguide/{email}")
    public ResponseEntity<List<ReservationGuide>> getReservationsByGuide(@PathVariable String email) {
        Guide guide = guideRepository.findByContact(email);
        if (guide == null) {
            return ResponseEntity.notFound().build();
        }
        List<ReservationGuide> reservations = ReservationGuideRepository.findByGuide(guide);
        return ResponseEntity.ok(reservations);
    }



    @GetMapping("byuser/{email}")
    public ResponseEntity<List<ReservationGuide>> getReservationsByUser(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<ReservationGuide> reservations = ReservationGuideRepository.findByUser(user);
        return ResponseEntity.ok(reservations);
    }

 /*   @PostMapping("/rate/{guideId}")
    public ResponseEntity<Void> rateReservation(@PathVariable Long guideId, @RequestBody ratingrequest ratingRequest) {
        ReservationGuide reservation = ReservationGuideRepository.findByGuideIdAndStatus(guideId, "accepted");
        if (reservation != null) {
            (reservation.getGuide()).setAverageRating(String.valueOf(ratingRequest.getRating()));
            ReservationGuideRepository.save(reservation);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }*/





        private final String SYSTRAN_API_KEY = "55f52d4d-6520-4f81-a801-0fd7e5d3f767";
        private final String SYSTRAN_URL = "https://api-translate.systran.net/translation/text/translate";
    @PostMapping("/translate")
    public String translate(@RequestParam String text, @RequestParam String targetLang) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        // Format correct pour l'authentification SYSTRAN
        headers.set("Authorization", "Key " + SYSTRAN_API_KEY); // Notez l'ajout de "Key "
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("input", text);
        params.add("target", targetLang);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(SYSTRAN_URL, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Log détaillé de l'erreur
            System.err.println("Erreur API SYSTRAN: " + e.getResponseBodyAsString());
            throw e;
        }
    }

}

