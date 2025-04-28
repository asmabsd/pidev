package com.example.pidev.entity.Gastronomy;

import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.GastronomyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailGastronomy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private boolean availability;
    private double rating;
    private String openingHours; // Horaires d’ouverture

    @Enumerated(EnumType.STRING)
    private GastronomyType type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gastronomy_id", referencedColumnName = "id")
    @JsonIgnore
    private Gastronomy gastronomy;
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public GastronomyType getType() {
        return type;
    }

    public void setType(GastronomyType type) {
        this.type = type;
    }

    public Gastronomy getGastronomy() {
        return gastronomy;
    }

    public void setGastronomy(Gastronomy gastronomy) {
        this.gastronomy = gastronomy;
    }
}
