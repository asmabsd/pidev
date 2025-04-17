package com.example.pidev.entity.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonSerialize // Ajouter pour forcer la sérialisation
public class Panel implements Serializable {
    private Date creationDate;
    private List<CommandLineDTO> commandLines;
    private double total;
    private int totalItems;


    public Panel() {
        this.creationDate = new Date();
        this.commandLines = new ArrayList<>();
        this.total = 0;
        this.totalItems = 0;
    }
//    public int getTotalItems() {
//        if (commandLines == null) return 0;
//        return commandLines.stream()
//                .mapToInt(CommandLineDTO::getQuantity)
//                .sum();
//    }
    public void addCommandLine(CommandLineDTO commandLineDTO) {
        this.commandLines.add(commandLineDTO);
        updateTotal();
    }

    public void removeCommandLine(int index) {
        if(index >= 0 && index < commandLines.size()) {
            commandLines.remove(index);
            updateTotal();
        }
    }

    public void updateTotal() {
        this.total = commandLines.stream()
                .mapToDouble(cl -> cl.getUnitPrice() * cl.getQuantity())
                .sum();
    }


    // Supprimer les setters inutiles
    public List<CommandLineDTO> getCommandLines() { return commandLines; }
    public double getTotal() { return total; }
    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCommandLines(List<CommandLineDTO> commandLines) {
        this.commandLines = commandLines;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getTotalItems() {
        int total = 0;
        if (commandLines != null) {
            for (CommandLineDTO line : commandLines) {
                total += line.getQuantity(); // Additionnez les quantités
            }
        }
        return total;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}