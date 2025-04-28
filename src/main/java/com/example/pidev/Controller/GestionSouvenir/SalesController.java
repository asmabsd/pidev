package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.entity.GestionSouvenir.MonthlySales;
import com.example.pidev.entity.GestionSouvenir.TopSellingSouvenir;
import com.example.pidev.repository.GestionSouvenir.CommandLineRepository;
import com.example.pidev.repository.GestionSouvenir.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin("http://localhost:4200")
public class SalesController {
    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private CommandLineRepository commandLineRepository;

    @GetMapping("/monthly/{storeId}")
    public List<MonthlySales> getMonthlySales(@PathVariable Long storeId) {
        return commandRepository.findMonthlySalesByStore(storeId);
    }

    @GetMapping("/top-souvenirs/{storeId}")
    public List<TopSellingSouvenir> getTopSellingSouvenirs(@PathVariable Long storeId) {
        return commandLineRepository.findTopSellingSouvenirsByStore(storeId);
    }

    @GetMapping("/detailsales/{storeId}")
    public List<CommandLine> getDetailedSales(@PathVariable Long storeId) {
        return commandLineRepository.findConfirmedSalesByStore(storeId);
    }
}
