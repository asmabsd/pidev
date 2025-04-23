package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.service.GestionSouvenir.IAService;
import com.example.pidev.service.GestionSouvenir.iSouvenirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/iA")
@Configuration
@CrossOrigin("http://localhost:4200")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class IaController {

    @Autowired
    IAService iaService;

    @Autowired
    iSouvenirService souvenirService;

    @PostMapping("/addSouvenir")
    Souvenir addSouvenir(@RequestBody Souvenir souvenir) {
        if (souvenir.getDescription() == null || souvenir.getDescription().isEmpty()) {
            String generatedDesc = iaService.generateDescription(
                    souvenir.getName(),
                    souvenir.getCategory().toString(),
                    souvenir.getPrice()
            );
            souvenir.setDescription(generatedDesc);
        }
        souvenir.updateStatus();
        return souvenirService.addSouvenir(souvenir);
    }

    @PostMapping("/generateDescription")
    public ResponseEntity<String> generateDescriptionFromFields(@RequestBody Map<String, Object> fields) {
        String name = (String) fields.get("name");
        String category = (String) fields.get("category");
        Double price = Double.valueOf(fields.get("price").toString());
        String desc = iaService.generateDescription(name, category, price);
        return ResponseEntity.ok(desc);
    }

}
