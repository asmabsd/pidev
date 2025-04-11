package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Souvenir;
import jakarta.servlet.http.HttpSession;

public interface iPanelService {

    void addToCart(HttpSession session, Souvenir souvenir);

    Panel getCart(HttpSession session);

    void removeFromCart(HttpSession session, CommandLine commandLine);

    void clearCart(HttpSession session);
}
