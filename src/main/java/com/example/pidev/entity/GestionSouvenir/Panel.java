package com.example.pidev.entity.GestionSouvenir;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Panel {

    private Date creationDate;
    private List<CommandLine> commandLines;
    private double total;

    public Panel() {
        this.creationDate = new Date();
        this.commandLines = new ArrayList<>();
        this.total = 0;
    }

    // Ajouter une CommandLine au Panel
    public void addCommandLine(CommandLine commandLine) {
        this.commandLines.add(commandLine);
        updateTotal();
    }

    // Supprimer une CommandLine spécifique
    public void removeCommandLine(CommandLine commandLine) {
        commandLines.remove(commandLine);
        updateTotal();
    }

    // Vider le panier
    public void clearPanel() {
        commandLines.clear();
        this.total = 0;
    }

    // Recalculer le total du panier
    private void updateTotal() {
        this.total = commandLines.stream().mapToDouble(CommandLine::getPrice).sum();
    }

    public List<CommandLine> getCommandLines() {
        return commandLines;
    }

    public double getTotal() {
        return total;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setCommandLines(List<CommandLine> commandLines) {
        this.commandLines = commandLines;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
