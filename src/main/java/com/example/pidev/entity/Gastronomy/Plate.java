package com.example.pidev.entity.Gastronomy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String namePlate;
    private String descriptionPlate;
    private double pricePlate;
    private String imagePlate;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonIgnoreProperties(value = {"descriptionMenu", "prixMenu", "gastronomy"})
    @JsonBackReference
    private Menu menu;

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour namePlate
    public String getNamePlate() {
        return namePlate;
    }

    public void setNamePlate(String namePlate) {
        this.namePlate = namePlate;
    }

    // Getter et Setter pour descriptionPlate
    public String getDescriptionPlate() {
        return descriptionPlate;
    }

    public void setDescriptionPlate(String descriptionPlate) {
        this.descriptionPlate = descriptionPlate;
    }

    // Getter et Setter pour pricePlate
    public double getPricePlate() {
        return pricePlate;
    }

    public void setPricePlate(double pricePlate) {
        this.pricePlate = pricePlate;
    }

    // Getter et Setter pour imagePlate
    public String getImagePlate() {
        return imagePlate;
    }

    public void setImagePlate(String imagePlate) {
        this.imagePlate = imagePlate;
    }

    // Getter et Setter pour menu
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    // Override de toString()
    @Override
    public String toString() {
        return "Plate{" +
                "id=" + id +
                ", namePlate='" + namePlate + '\'' +
                ", descriptionPlate='" + descriptionPlate + '\'' +
                ", pricePlate=" + pricePlate +
                ", imagePlate='" + imagePlate + '\'' +
                ", menu=" + (menu != null ? menu.getId() : null) +
                '}';
    }

}
