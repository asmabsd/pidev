package com.example.pidev.entity.hebergement;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hebergement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hebergement;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeHebergement type;

    private String adresse;
    private String description;
    private String availability;
    private Long price;

    /*@ManyToOne
    User user;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hebergement")
    private Set<ReservationChambre> reservationchambres;

    public Long getId_hebergement() {
        return id_hebergement;
    }

    public String getName() {
        return name;
    }

    public TypeHebergement getType() {
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

    public Set<ReservationChambre> getReservationchambres() {
        return reservationchambres;
    }

    public void setId_hebergement(Long id_hebergement) {
        this.id_hebergement = id_hebergement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TypeHebergement type) {
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

    public void setReservationchambres(Set<ReservationChambre> reservationchambres) {
        this.reservationchambres = reservationchambres;
    }
}
