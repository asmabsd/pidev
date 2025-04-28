package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.entity.GestionSouvenir.Command;
import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.entity.GestionSouvenir.Panel;
import com.example.pidev.entity.GestionSouvenir.TopSellingSouvenir;

import java.util.List;

public interface iCommandLineService {
    public List<CommandLine> createFromPanel(Panel panel, Command command) ;
    public CommandLine convertToEntity(CommandLineDTO dto, Command command);
    public List<CommandLineDTO> getCommandLinesForOrder(Long commandId);
    public CommandLineDTO convertToDTO(CommandLine entity);
    List<TopSellingSouvenir> findTopSellingSouvenirsByStore( Long storeId);
    List<CommandLine> findConfirmedSalesByStore(Long storeId);

}
