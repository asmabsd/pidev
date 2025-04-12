package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.service.GestionSouvenir.iCommandLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/commands/{commandId}/lines")
public class CommandLineController {
    @Autowired
    iCommandLineService commandLineService;

    
    @GetMapping
    public ResponseEntity<List<CommandLineDTO>> getCommandLines(@PathVariable Long commandId) {
        return ResponseEntity.ok(commandLineService.getCommandLinesForOrder(commandId));
    }
}
