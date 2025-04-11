package com.example.pidev.entity.hebergement;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

<<<<<<< HEAD
import java.time.LocalDate;

=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
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
<<<<<<< HEAD
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nombreadulte;
    private int nombrenfant;
    private String statut;
    private Long prixTotal;


    @ManyToOne
    @JoinColumn(name = "id_hebergement")
    @JsonIgnoreProperties("reservationchambres") // Ignore la collection inverse dans Hebergement pour éviter la boucle
     Hebergement hebergement;
=======

    private String name;

    private String type;

    private String adresse;
    private String description;
    private String availability;
    private Long price;


    @ManyToOne
    Hebergement hebergement;
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2

    public Long getId_reservation() {
        return id_reservation;
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public void setId_reservation(Long id_reservation) {
        this.id_reservation = id_reservation;
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }
<<<<<<< HEAD

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
=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
}
