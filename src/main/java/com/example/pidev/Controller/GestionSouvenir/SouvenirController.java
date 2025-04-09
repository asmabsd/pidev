package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.service.GestionSouvenir.iSouvenirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/souvenir")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin("http://localhost:4200")
public class SouvenirController {
    @Autowired
    iSouvenirService souvenirService;

    @PostMapping("/addSouvenir")
    Souvenir addSouvenir(@RequestBody Souvenir souvenir) {
        souvenir.updateStatus();
        return souvenirService.addSouvenir(souvenir);
    }

    @PutMapping("/updateSouvenir")
    Souvenir souvenirUpdate(@RequestBody Souvenir souvenir) {
        souvenir.updateStatus();
        return souvenirService.updateSouvenir(souvenir);
    }

    @DeleteMapping("/deleteSouvenir")
    void souvenirDelete(@RequestParam long id) {
        souvenirService.deleteSouvenir(id);
    }

    @GetMapping("/retrieveAllSouvenir")
    List<Souvenir> retrieveAllSouvenir() {
        return souvenirService.retrieveAllSouvenir();
    }

    @GetMapping("/retrieveSouvenir/{id}")
    Souvenir retrieveSouvenir(@PathVariable Long id) {
        return souvenirService.retrieveSouvenir(id);
    }


}
