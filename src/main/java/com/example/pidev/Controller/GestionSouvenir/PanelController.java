package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.service.GestionSouvenir.iPanelService;
import com.example.pidev.service.GestionSouvenir.iSouvenirService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panel")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin("http://localhost:4200")
public class PanelController {
    @Autowired
    iPanelService panelService;
    @Autowired
    iSouvenirService souvenirService;


    @PostMapping("/add")
    public Panel addToCart(@RequestParam Long souvenirId,
                           @RequestParam(defaultValue = "1") int quantity,
                           HttpSession session) {
        Souvenir souvenir = souvenirService.retrieveSouvenir(souvenirId);
        panelService.addToCart(session, souvenir, quantity);
        return panelService.getCart(session);
    }

    @PatchMapping("/update/{index}")
    public Panel updateQuantity(@PathVariable int index,
                                @RequestParam int quantity,
                                HttpSession session) {
        panelService.updateQuantity(session, index, quantity);
        return panelService.getCart(session);
    }

    @GetMapping
    public Panel viewCart(HttpSession session) {
        return panelService.getCart(session);
    }

    @DeleteMapping("/remove/{index}")
    public Panel removeFromCart(@PathVariable int index, HttpSession session) {
        panelService.removeFromCart(session, index);
        return panelService.getCart(session);
    }

    @DeleteMapping("/clear")
    public void clearCart(HttpSession session) {
        panelService.clearCart(session);
    }
}