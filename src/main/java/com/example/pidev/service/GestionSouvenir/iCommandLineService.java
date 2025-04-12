package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.Command;

import jakarta.servlet.http.HttpSession;
import com.example.pidev.entity.GestionSouvenir.CommandLine;

import java.util.List;

public interface iCommandLineService {
    public List<CommandLine> createFromPanel(Panel panel, Command command) ;
    public CommandLine convertToEntity(CommandLineDTO dto, Command command);
    public List<CommandLineDTO> getCommandLinesForOrder(Long commandId);
    public CommandLineDTO convertToDTO(CommandLine entity);
}
