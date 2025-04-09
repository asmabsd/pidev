package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.service.GestionSouvenir.iStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin("http://localhost:4200")
public class StoreController {
    @Autowired
    iStoreService storeService;

    @PostMapping("/addStore")
    Store storeAdd(@RequestBody Store store) {
        return storeService.addStore(store);
    }

    @PutMapping("/updateStore")
    Store storeUpdate(@RequestBody Store store) {
        return storeService.updateStore(store);
    }

    @DeleteMapping("/deleteStore")
    void storeDelete(@RequestParam long id) {
        storeService.deleteStore(id);
    }

    @GetMapping("/retrieveAllStore")
    List<Store> retrieveAllStore() {
        return storeService.retrieveAllStore();
    }

    @GetMapping("/retrieveStore")
    Store retrieveStore(@RequestParam long id) {
        return storeService.retrieveStore(id);
    }
}
