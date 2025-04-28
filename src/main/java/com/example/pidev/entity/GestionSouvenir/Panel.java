package com.example.pidev.entity.GestionSouvenir;

import com.example.pidev.dtos.GestionSouvenir.CommandLineDTO;
import com.example.pidev.entity.User.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

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
    private double discount;
    private String appliedDiscountCode;
    private transient Discount appliedDiscount; // Champ transient pour la session
    private double subtotal; // Doit être inclus dans le JSON

    // Ajouter explicitement les getters/setters pour Jackson
    public double getSubtotal() {
        return this.subtotal;
    }


    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void updateTotal() {
        // 1. Calculer le sous-total (sans réduction)
        this.subtotal = commandLines.stream()
                .mapToDouble(cl -> cl.getUnitPrice() * cl.getQuantity())
                .sum();

        // 2. Réinitialiser le montant de la réduction
        double discountAmount = 0;

        // 3. Si une réduction est appliquée, recalculer
        if (appliedDiscount != null) {
            switch (appliedDiscount.getType()) {
                case PERCENTAGE:
                    discountAmount = subtotal * (appliedDiscount.getValue() / 100);
                    break;
                case FIXED:
                    discountAmount = appliedDiscount.getValue();
                    break;
                case BUNDLE:
                    long applicableItems = commandLines.stream()
                            .filter(cl -> cl.getSouvenir().getCategory()
                                    .equalsIgnoreCase(appliedDiscount.getApplicableCategory()))
                            .mapToInt(CommandLineDTO::getQuantity)
                            .sum();
                    int bundles = (int) (applicableItems / appliedDiscount.getMinItems());
                    discountAmount = bundles * appliedDiscount.getValue();
                    break;
            }
            // S'assurer que la réduction ne dépasse pas le sous-total
            discountAmount = Math.min(discountAmount, subtotal);
        }

        // 4. Calculer le total final
        this.total = Math.max(0, subtotal - discountAmount);
    }

    public void applyDiscount(Discount discount) {
        this.appliedDiscount = discount;
        this.appliedDiscountCode = discount.getCode();
        updateTotal(); // Recalcul immédiat
    }

    public void removeDiscount() {
        this.appliedDiscount = null;
        this.appliedDiscountCode = null;
        updateTotal(); // Recalcul immédiat
    }

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
        this.totalItems += commandLineDTO.getQuantity(); // Mise à jour manuelle
        updateTotal();
    }

    public void removeCommandLine(int index) {
        if (index >= 0 && index < commandLines.size()) {
            CommandLineDTO removedLine = commandLines.remove(index);
            this.totalItems -= removedLine.getQuantity(); // Mise à jour manuelle

            updateTotal();
        }
    }


    // Supprimer les setters inutiles
    public List<CommandLineDTO> getCommandLines() {
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

    public void setCommandLines(List<CommandLineDTO> commandLines) {
        this.commandLines = commandLines;
        this.totalItems = commandLines.stream()
                .mapToInt(CommandLineDTO::getQuantity)
                .sum();
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

    @OneToOne
    User user;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getAppliedDiscountCode() {
        return appliedDiscountCode;
    }

    public void setAppliedDiscountCode(String appliedDiscountCode) {
        this.appliedDiscountCode = appliedDiscountCode;
    }

    public Discount getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Discount appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }
}