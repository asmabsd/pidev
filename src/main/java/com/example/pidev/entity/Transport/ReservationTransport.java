package com.example.pidev.entity.Transport;

import com.example.pidev.entity.User.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@ToString(exclude = {"transport", "user"})

public class ReservationTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Le point de départ est obligatoire.")
    private String departPoint;

    @NotBlank(message = "La destination est obligatoire.")
    private String destination;

    @Future(message = "La date de départ doit être dans le futur.")
    @NotNull(message = "L'heure de départ est obligatoire.")
    private Date departureHour;

    @Min(value = 0, message = "Le prix doit être positif.")
    private int price;

    @NotBlank(message = "Le statut est obligatoire.")
    private String status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = true)
    private Transport transport;

    @ManyToOne
    @JsonIgnoreProperties("reservations")

    @JoinColumn(nullable = true)
    @JsonIgnore
    private User user;
}

