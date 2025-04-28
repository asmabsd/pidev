package com.example.pidev.entity.hebergement;

import com.example.pidev.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Le nom ne peut pas être nul")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    private String name;

    @NotBlank(message = "Le type d'hébergement est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeHebergement type;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(min = 5, max = 200, message = "L'adresse doit contenir entre 5 et 200 caractères")
    private String adresse;

    @NotBlank(message = "La description est obligatoire")
    @Size(min = 10, max = 500, message = "La description doit contenir entre 10 et 500 caractères")
    private String description;

    @NotBlank(message = "La disponibilité est obligatoire")
    @Pattern(regexp = "^(Disponible|Indisponible)$", message = "La disponibilité doit être 'Disponible' ou 'Indisponible'")
    private String availability;

    @NotBlank(message = "Le prix est obligatoire")
    @Min(value = 0, message = "Le prix doit être positif")
    private Long price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hebergement")
    @JsonIgnoreProperties("hebergement") // Ignore le champ hebergement dans chaque réservation

    private Set<ReservationChambre> reservationchambres;
    private String region;
    private String telephone;




    public Long getId_hebergement() {
        return id_hebergement;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;


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
    @ManyToOne
    User user;

    public User getUser() {
        return user;
    }

    public String getRegion() {
        return region;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private int nbChambre;

    public int getNbChambre() {
        return nbChambre;
    }

    public void setNbChambre(int nbChambre) {
        this.nbChambre = nbChambre;
    }
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private int nombreReservations = 0;
    public int getNombreReservations() {
        return nombreReservations;
    }

    public void setNombreReservations(int nombreReservations) {
        this.nombreReservations = nombreReservations;
    }
    private int totalSingleChambres ;
    private int totalDoubleChambres ;
    private int totalSuiteChambres ;
    private int totalDelexueChambres ;

    public int getTotalSingleChambres() {
        return totalSingleChambres;
    }

    public int getTotalDoubleChambres() {
        return totalDoubleChambres;
    }

    public int getTotalSuiteChambres() {
        return totalSuiteChambres;
    }

    public int getTotalDelexueChambres() {
        return totalDelexueChambres;
    }

    public void setTotalSingleChambres(int totalSingleChambres) {
        this.totalSingleChambres = totalSingleChambres;
    }

    public void setTotalDoubleChambres(int totalDoubleChambres) {
        this.totalDoubleChambres = totalDoubleChambres;
    }

    public void setTotalSuiteChambres(int totalSuiteChambres) {
        this.totalSuiteChambres = totalSuiteChambres;
    }

    public void setTotalDelexueChambres(int totalDelexueChambres) {
        this.totalDelexueChambres = totalDelexueChambres;
    }
}
