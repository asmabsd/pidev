package com.example.pidev.entity.hebergement;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nombrePersonnes;

    private String statut;
    private Long prixTotal;


    @ManyToOne
    Hebergement hebergement;

    public Long getId_reservation() {
        return id_reservation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }



    public String getStatut() {
        return statut;
    }

    public Long getPrixTotal() {
        return prixTotal;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public void setId_reservation(Long id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }



    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setPrixTotal(Long prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }
}
