package com.example.pidev.Interface.hebergement;

import com.example.pidev.entity.hebergement.ReservationChambre;

import java.util.List;

public interface IReservationChambreService {
    ReservationChambre addReservationChambre(ReservationChambre reservationchambre);
    List<ReservationChambre> getReservationChambres();
<<<<<<< HEAD
    ReservationChambre updateReservationChambre(ReservationChambre reservationchambre); // Pas de changement ici
=======
    ReservationChambre updateReservationChambre(ReservationChambre reservationchambre);
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
    void deleteReservationChambre(Long reservationchambre_id);
    ReservationChambre getReservationChambre(Long id);

}
