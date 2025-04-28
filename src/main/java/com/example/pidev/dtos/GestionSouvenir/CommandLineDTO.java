package com.example.pidev.dtos.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;

import java.io.Serializable;

public class CommandLineDTO implements Serializable {

    private Souvenir souvenir;
    private int quantity;
    private double unitPrice;

    public CommandLineDTO(Souvenir souvenir, int quantity, double unitPrice) {
        this.souvenir = souvenir;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public CommandLineDTO() {
    }

    public Souvenir getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(Souvenir souvenir) {
        this.souvenir = souvenir;
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
}
