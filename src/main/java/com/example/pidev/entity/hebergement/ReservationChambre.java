package com.example.pidev.entity.hebergement;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ReservationChambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reservation;

    private String name;

    private String type;

    private String adresse;
    private String description;
    private String availability;
    private Long price;


    @ManyToOne
    Hebergement hebergement;

    public Long getId_reservation() {
        return id_reservation;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDescription() {
        return description;
    }

    public String getAvailability() {
        return availability;
    }

    public Long getPrice() {
        return price;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public void setId_reservation(Long id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }
}
