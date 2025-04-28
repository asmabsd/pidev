package com.example.pidev.entity.Gastronomy;

import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") //
//@JsonIgnoreProperties({"name", "type", "location", "image", "menus"})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameMenu;
    private String descriptionMenu;
    private double prixMenu;

    @ManyToOne
    @JoinColumn(name = "gastronomy_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"name", "type", "location", "image", "menus"})
    private Gastronomy gastronomy;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Plate> plates = new ArrayList<>();

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    public String getDescriptionMenu() {
        return descriptionMenu;
    }

    public void setDescriptionMenu(String descriptionMenu) {
        this.descriptionMenu = descriptionMenu;
    }

    public double getPrixMenu() {
        return prixMenu;
    }

    public void setPrixMenu(double prixMenu) {
        this.prixMenu = prixMenu;
    }

    public Gastronomy getGastronomy() {
        return gastronomy;
    }

    public void setGastronomy(Gastronomy gastronomy) {
        this.gastronomy = gastronomy;
    }
    public List<Plate> getPlates() {
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }


}
