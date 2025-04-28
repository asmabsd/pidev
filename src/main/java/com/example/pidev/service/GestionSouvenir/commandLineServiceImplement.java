package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.entity.GestionSouvenir.*;
import com.example.pidev.exception.InsufficientStockException;
import com.example.pidev.repository.GestionSouvenir.CommandLineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class commandLineServiceImplement implements iCommandLineService {
    @Autowired
    CommandLineRepository commandLineRepository;
    @Autowired
    iSouvenirService souvenirService;


    @Override
    public List<CommandLine> createFromPanel(Panel panel, Command command) {
        return panel.getCommandLines().stream()
                .map(dto -> convertToEntity(dto, command))
                .peek(commandLineRepository::save)
                .toList();
    }

    @Override
    public CommandLine convertToEntity(CommandLineDTO dto, Command command) {
        // Dans commandLineServiceImplement.java

        Souvenir souvenir = souvenirService.retrieveSouvenir(dto.getSouvenir().getId());

        if (souvenir.getQuantity() < dto.getQuantity()) {
            throw new InsufficientStockException("Stock insuffisant pour " + souvenir.getName());
        }

        // Utiliser le constructeur qui initialise unitPrice
        return new CommandLine(souvenir, dto.getQuantity(), command);
    }

    @Override
    public List<CommandLineDTO> getCommandLinesForOrder(Long commandId) {
        return commandLineRepository.findByCommandId(commandId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public CommandLineDTO convertToDTO(CommandLine entity) {
        CommandLineDTO dto = new CommandLineDTO();
        dto.setSouvenir(entity.getSouvenir());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        return dto;
    }

    @Override
    public List<TopSellingSouvenir> findTopSellingSouvenirsByStore(Long storeId) {
        return commandLineRepository.findTopSellingSouvenirsByStore(storeId);    }

    @Override
    public List<CommandLine> findConfirmedSalesByStore(Long storeId) {
        return commandLineRepository.findConfirmedSalesByStore(storeId);
    }

}

