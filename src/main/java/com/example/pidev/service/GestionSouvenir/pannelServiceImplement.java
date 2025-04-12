package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.exception.InsufficientStockException;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class pannelServiceImplement implements iPanelService {
    private static final String CART_SESSION_KEY = "panel";
    @Override
    public void addToCart(HttpSession session, Souvenir souvenir, int quantity) {
        Panel panel = getOrCreatePanel(session);

        // Trouver la ligne existante par ID de souvenir
        Optional<CommandLineDTO> existingLineOpt = panel.getCommandLines().stream()
                .filter(line -> line.getSouvenir().getId().equals(souvenir.getId()))
                .findFirst();

        if (existingLineOpt.isPresent()) {
            CommandLineDTO existingLine = existingLineOpt.get();

            // Valider le stock AVANT mise à jour
            int newQuantity = existingLine.getQuantity() + quantity;
            if (souvenir.getQuantity() < newQuantity) {
                throw new InsufficientStockException("Stock insuffisant pour " + souvenir.getName()
                        + ". Disponible: " + souvenir.getQuantity());
            }

            existingLine.setQuantity(newQuantity);

            // Supprimer si quantité <= 0
            if (newQuantity <= 0) {
                panel.getCommandLines().remove(existingLine);
            }

        } else {
            // Valider le stock pour nouvelle ligne
            if (souvenir.getQuantity() < quantity) {
                throw new InsufficientStockException("Stock insuffisant pour " + souvenir.getName());
            }

            CommandLineDTO newLine = new CommandLineDTO(souvenir, quantity, souvenir.getPrice());
            panel.addCommandLine(newLine);
        }

        panel.updateTotal();
        session.setAttribute(CART_SESSION_KEY, panel);
    }

    @Override
    public void updateQuantity(HttpSession session, int itemIndex, int newQuantity) {
        Panel panel = getOrCreatePanel(session);
        if(itemIndex >= 0 && itemIndex < panel.getCommandLines().size()) {
            CommandLineDTO line = panel.getCommandLines().get(itemIndex);
            line.setQuantity(newQuantity);
            panel.updateTotal();
        }
    }

    private Panel getOrCreatePanel(HttpSession session) {
        Panel panel = (Panel) session.getAttribute(CART_SESSION_KEY);
        if(panel == null) {
            panel = new Panel();
            session.setAttribute(CART_SESSION_KEY, panel);
        }
        return panel;
    }

    @Override
    public Panel getCart(HttpSession session) {
        return getOrCreatePanel(session);
    }

    @Override
    public void removeFromCart(HttpSession session, int itemIndex) {
        Panel panel = getOrCreatePanel(session);
        panel.removeCommandLine(itemIndex);
    }

    @Override
    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
