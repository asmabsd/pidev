package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.service.GestionSouvenir.iPanelService;
import com.example.pidev.service.GestionSouvenir.iSouvenirService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Pannel")
public class PanelController {
    @Autowired
    iPanelService iPanelService;

    @Autowired
    iSouvenirService souvenirService;

    @PostMapping("/add")
    public Panel addToCart(@RequestParam Long souvenirId, HttpSession session) {
        Souvenir souvenir = souvenirService.retrieveSouvenir(souvenirId); // Normalement, à récupérer depuis la base de données
        iPanelService.addToCart(session, souvenir);
        return iPanelService.getCart(session);
    }

    @GetMapping("/view")
    public Panel viewCart(HttpSession session) {
        return iPanelService.getCart(session);
    }

    @DeleteMapping("/remove")
    public void removeFromCart(HttpSession session, CommandLine commandLine) {
        iPanelService.removeFromCart(session, commandLine);
    }

    @DeleteMapping("/clear")
    public void clearCart(HttpSession session) {
        iPanelService.clearCart(session);
    }

}
