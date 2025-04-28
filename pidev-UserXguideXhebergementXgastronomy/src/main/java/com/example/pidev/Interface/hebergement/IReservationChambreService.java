package com.example.pidev.Interface.hebergement;

import com.example.pidev.entity.hebergement.ReservationChambre;

import java.util.List;

public interface IReservationChambreService {
    ReservationChambre addReservationChambre(ReservationChambre reservationchambre);
    List<ReservationChambre> getReservationChambres();
    ReservationChambre updateReservationChambre(ReservationChambre reservationchambre);
    void deleteReservationChambre(Long reservationchambre_id);
    ReservationChambre getReservationChambre(Long id);

}
