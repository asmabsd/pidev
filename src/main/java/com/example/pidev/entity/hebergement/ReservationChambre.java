package com.example.pidev.entity.hebergement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int nombreadulte;
    private int nombrenfant;
    private String statut;
    private Long prixTotal;
    private String clientEmail;
    private int nombreChambres;
    @Enumerated(EnumType.STRING)
    private TypeChambre typeChambre;
    @ManyToOne
    @JoinColumn(name = "id_hebergement")
    @JsonIgnoreProperties("reservationchambres") // Ignore la collection inverse dans Hebergement pour éviter la boucle
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




    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setPrixTotal(Long prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }

    public int getNombreadulte() {
        return nombreadulte;
    }

    public int getNombrenfant() {
        return nombrenfant;
    }

    public void setNombreadulte(int nombreadulte) {
        this.nombreadulte = nombreadulte;
    }

    public void setNombrenfant(int nombrenfant) {
        this.nombrenfant = nombrenfant;
    }


    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getNombreChambres() {
        return nombreChambres;
    }



    public void setNombreChambres(int nombreChambres) {
        this.nombreChambres = nombreChambres;
    }

    public TypeChambre getTypeChambre() {
        return typeChambre;
    }

    public void setTypeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
    }
}
