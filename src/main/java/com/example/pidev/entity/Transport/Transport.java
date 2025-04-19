package com.example.pidev.entity.Transport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String disponibilite;
    private String location;
    private String description;
    private String capacity;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReservationTransport> reservations;

}
