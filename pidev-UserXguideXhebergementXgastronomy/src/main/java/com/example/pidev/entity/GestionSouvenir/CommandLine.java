package com.example.pidev.entity.GestionSouvenir;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommandLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double price;

    @ManyToOne
    private Souvenir souvenir;

    @ManyToOne
    private Command command;

    public CommandLine(Souvenir souvenir, int quantity) {
        this.souvenir = souvenir;
        this.quantity = quantity;
        if (souvenir != null) {
            this.price = souvenir.getPrice();
        } else {
            this.price = 0; // Or some default value if souvenir is null
        }
        updatePriceFromQuantity();
    }

    public Long getId() {
        return id;
    }

    public void updatePriceFromQuantity() {
        this.price = this.quantity * this.price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        updatePriceFromQuantity();
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updatePriceFromQuantity(); // Ensure price is updated when quantity changes
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Souvenir getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(Souvenir souvenir) {
        this.souvenir = souvenir;
        if (souvenir != null) {
            this.price = souvenir.getPrice();
        } else {
            this.price = 0; // Handle null case
        }
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CommandLine that = (CommandLine) obj;
        return Objects.equals(souvenir, that.souvenir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(souvenir);
    }
}
