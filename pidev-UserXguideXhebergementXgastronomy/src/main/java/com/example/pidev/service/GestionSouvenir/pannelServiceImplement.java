package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.repository.GestionSouvenir.CommandLineRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class pannelServiceImplement implements iPanelService {
    @Autowired
    CommandLineRepository commandLineRepository;
    private static final String CART_SESSION_KEY = "panel";

    @Override
    public void addToCart(HttpSession session, Souvenir souvenir) {
        Panel panel = (Panel) session.getAttribute(CART_SESSION_KEY);

        if (panel == null) {
            panel = new Panel();
        }
        // Vérifier si le souvenir est déjà dans le panier
        CommandLine existingCommandLine = panel.getCommandLines().stream()
                .filter(line -> line.getSouvenir().equals(souvenir))
                .findFirst()
                .orElse(null);
        if (existingCommandLine != null) {
            // Si le produit est déjà dans le panier, mettre à jour la quantité
            existingCommandLine.setQuantity(existingCommandLine.getQuantity() + 1);
            existingCommandLine.updatePriceFromQuantity(); // Recalculer le prix basé sur la quantité
            commandLineRepository.save(existingCommandLine); // Sauvegarder les modifications dans la base
        } else {
            // Sinon, créer une nouvelle ligne de commande avec la quantité spécifiée
            CommandLine commandLine = new CommandLine(souvenir, 1); // Command peut être null si non spécifiée
            commandLine.updatePriceFromQuantity(); // Calculer le prix en fonction de la quantité
            commandLineRepository.save(commandLine); // Sauvegarder la nouvelle ligne dans la base
            panel.addCommandLine(commandLine); // Ajouter la ligne au panier
        }
        session.setAttribute(CART_SESSION_KEY, panel);
    }

    @Override
    public Panel getCart(HttpSession session) {
        Panel panel = (Panel) session.getAttribute(CART_SESSION_KEY);
        return (panel != null) ? panel : new Panel();
    }

    @Override
    public void removeFromCart(HttpSession session, CommandLine commandLine) {
        Panel panel = (Panel) session.getAttribute(CART_SESSION_KEY);
        if (panel != null) {
            panel.removeCommandLine(commandLine);
            commandLineRepository.deleteById(commandLine.getId());
            session.setAttribute(CART_SESSION_KEY, panel);
        }
    }

    @Override
    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
