package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.entity.GestionSouvenir.Discount;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Souvenir;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface iPanelService {
    void addToCart(HttpSession session, Souvenir souvenir, int quantity);
    void updateQuantity(HttpSession session, int itemIndex, int newQuantity);
    Panel getCart(HttpSession session);
    void removeFromCart(HttpSession session, int itemIndex);
    void clearCart(HttpSession session);
    Panel updateEntireCart(HttpSession session, List<CommandLineDTO> updatedLines);
    void applyDiscount(Panel panel, Discount discount);
}
