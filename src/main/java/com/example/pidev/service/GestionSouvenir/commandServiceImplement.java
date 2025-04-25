package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.*;
import com.example.pidev.exception.InsufficientStockException;
import com.example.pidev.exception.NotFoundException;
import com.example.pidev.repository.GestionSouvenir.CommandRepository;
import com.example.pidev.repository.GestionSouvenir.SouvenirRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class commandServiceImplement implements iCommandService {
    @Autowired
    iCommandLineService commandLineService;

    @Autowired
    CommandRepository commandRepository;

    @Autowired
    SouvenirRepository souvenirRepository;

    @Transactional
    @Override
    public Command createCommandFromPanel(Panel panel) {
        Command command = new Command();
        // Conversion des lignes
        List<CommandLine> lines = commandLineService.createFromPanel(panel, command);
        command.setCommandLines(lines);

        // Ne PAS réappliquer la réduction ici
        command.setTotal(panel.getTotal()); // Prend directement le total déjà réduit

        command.setStatus(CommandStatus.PENDING);
        return commandRepository.save(command);
    }

    @Override
    @Transactional
    public Command finalizeCommand(Long commandId) {
        Command command = commandRepository.findById(commandId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        // Vérification renforcée du statut
        if (command.getStatus() != CommandStatus.PENDING) {
            throw new IllegalStateException("Seules les commandes PENDING peuvent être finalisées");
        }

        // Vérification du stock AVANT toute modification
        command.getCommandLines().forEach(line -> {
            Souvenir souvenir = line.getSouvenir();
            if (souvenir.getQuantity() < line.getQuantity()) {
                throw new InsufficientStockException(
                        "Stock insuffisant pour " + souvenir.getName() +
                                " (Stock restant: " + souvenir.getQuantity() + ")"
                );
            }
        });

        // Mise à jour du stock
        command.getCommandLines().forEach(line -> {
            Souvenir souvenir = line.getSouvenir();
            souvenir.setQuantity(souvenir.getQuantity() - line.getQuantity());
            souvenirRepository.save(souvenir);
        });

        command.setStatus(CommandStatus.CONFIRMED);
        return commandRepository.save(command);
    }

    @Override
    @Transactional
    public Command cancelCommand(Long commandId) {
        Command command = commandRepository.findById(commandId)
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        if (command.getStatus() == CommandStatus.CANCELED) {
            throw new IllegalStateException("La commande est déjà annulée");
        }

        if (command.getStatus() == CommandStatus.CONFIRMED) {
            command.getCommandLines().forEach(line -> {
                Souvenir souvenir = line.getSouvenir();
                souvenir.setQuantity(souvenir.getQuantity() + line.getQuantity());
                souvenirRepository.save(souvenir);
            });
        }


        command.setStatus(CommandStatus.CANCELED);
        return commandRepository.save(command);
    }

    @Override
    public List<MonthlySales> findMonthlySales(Long storeId) {
        return commandRepository.findMonthlySalesByStore(storeId);
    }

}
