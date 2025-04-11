package com.example.pidev.entity.Transport;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTransport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String departPoint;
    private String destination;
    private int idUser;
    private Date departureHour;
    private int price;
    private String status;

    @ManyToOne
    @JoinColumn(name = "idTransport", nullable = false)
    private Transport transport;
}
