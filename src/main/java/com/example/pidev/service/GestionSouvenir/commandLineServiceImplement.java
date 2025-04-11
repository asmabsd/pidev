package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.repository.GestionSouvenir.CommandLineRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.pidev.entity.GestionSouvenir.CommandLine;

import java.util.List;

@Service
public class commandLineServiceImplement implements iCommandLineService {
    @Autowired
    CommandLineRepository commandLineRepository;

    @Override
    public CommandLine addCommandLine(CommandLine commandLine) {
        return commandLineRepository.save(commandLine);
    }

    private static final String CART_SESSION_KEY = "panel";

    @Override
    public CommandLine updateCommandLine(HttpSession session, CommandLine commandLine) {
        commandLine.updatePriceFromQuantity();
        commandLineRepository.save(commandLine);
        Panel panel = (Panel) session.getAttribute(CART_SESSION_KEY);

        if (panel == null) {
            panel = new Panel();
        }
        CommandLine existingCommandLine = panel.getCommandLines().stream()
                .filter(line -> line.getSouvenir().equals(commandLine.getSouvenir()))
                .findFirst()
                .orElse(null);

        existingCommandLine.setQuantity(commandLine.getQuantity());
        existingCommandLine.updatePriceFromQuantity();
        return commandLineRepository.save(existingCommandLine);
    }

    @Override
    public void deleteCommandLine(Long idCommandLine) {
        commandLineRepository.deleteById(idCommandLine);
    }

    @Override
    public List<CommandLine> retrieveAllCommandLine() {
        return commandLineRepository.findAll();
    }

    @Override
    public CommandLine retrieveCommandLine(Long idCommandLine) {
        return commandLineRepository.findById(idCommandLine).get();
    }
}
