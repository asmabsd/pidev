    package com.example.pidev.Controller.GestionSouvenir;

    import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
    import com.example.pidev.entity.GestionSouvenir.Panel;
    import com.example.pidev.entity.GestionSouvenir.Souvenir;

    import com.example.pidev.service.GestionSouvenir.iPanelService;
    import com.example.pidev.service.GestionSouvenir.iSouvenirService;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/panel")
    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", methods = {RequestMethod.PATCH, RequestMethod.OPTIONS})
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

       /* @PatchMapping("/update/{index}")
        public Panel updateQuantity(@PathVariable int index,
                                    @RequestParam int quantity,
                                    HttpSession session) {
            panelService.updateQuantity(session, index, quantity);
            return panelService.getCart(session);
        }*/


        @PatchMapping("/update/{index}/{quantity}")
        public Panel updateQuantity(@PathVariable int index,
                                    @PathVariable int quantity,
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

        @PutMapping("/update")
        public Panel updateEntireCart(@RequestBody List<Map<String, Object>> updates, HttpSession session) {
            // Conversion des données reçues
            List<CommandLineDTO> lines = new ArrayList<>();

            for (Map<String, Object> update : updates) {
                Long souvenirId = Long.parseLong(update.get("souvenirId").toString());
                int quantity = Integer.parseInt(update.get("quantity").toString());

                Souvenir souvenir = souvenirService.retrieveSouvenir(souvenirId);
                lines.add(new CommandLineDTO(souvenir, quantity, souvenir.getPrice()));
            }

            return panelService.updateEntireCart(session, lines);
        }


    }