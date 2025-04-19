package com.example.pidev.Interface.hebergement;

import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.ReservationChambre;
import com.example.pidev.entity.hebergement.TypeHebergement;

import java.util.List;

public interface IHebergementService {
    Hebergement addHebergement(Hebergement hebergement);
    List<Hebergement> getHebergements();
    Hebergement updateHebergement(Hebergement hebergement);
    void deleteHebergement(Long hebergement_id);
    Hebergement getHebergement(Long id);
    public Hebergement affecterReservationAHebergement(Long idHebergement, Long idReservation) ;
    public ReservationChambre ajouterReservationEtAffecter(Long idHebergement, ReservationChambre reservation) ;
    public Hebergement affecterReservationsAHebergement(Long idHebergement, List<Long> idReservations) ;
    List<ReservationChambre> getReservationsByHebergement(Long idHebergement);
    Hebergement rateHebergement(Long idHebergement, int rating);

}
