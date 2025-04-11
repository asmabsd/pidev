package com.example.pidev.entity.hebergement;

<<<<<<< HEAD
import com.example.pidev.entity.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
=======
import jakarta.persistence.*;
import lombok.*;

>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
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

<<<<<<< HEAD
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
=======
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

>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
    public Long getId_hebergement() {
        return id_hebergement;
    }

<<<<<<< HEAD

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
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
<<<<<<< HEAD
    @ManyToOne
    User user;

    public User getUser() {
        return user;
    }
=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
}
