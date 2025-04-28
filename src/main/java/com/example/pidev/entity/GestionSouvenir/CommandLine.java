package com.example.pidev.entity.GestionSouvenir;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class CommandLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Souvenir souvenir;

    @ManyToOne
    @JsonIgnore
    private Command command;

    private int quantity;
    private double unitPrice; // Prix figé au moment de la commande
    private LocalDateTime createdAt;


    // Constructeurs
    // Dans CommandLine.java
    public CommandLine(Souvenir souvenir, int quantity, Command command) {
        this.souvenir = souvenir;
        this.quantity = quantity;
        this.command = command;
        this.unitPrice = souvenir.getPrice(); // Ajouter cette ligne
        this.createdAt = LocalDateTime.now();
    }

    public CommandLine() {
    }


    // Getters/Setters
    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Souvenir getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(Souvenir souvenir) {
        this.souvenir = souvenir;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
