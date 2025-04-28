package com.example.pidev.service.hebergement;

import com.example.pidev.Interface.hebergement.IHebergementService;
import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.ReservationChambre;
import com.example.pidev.repository.hebergement.HebergementRepository;
import com.example.pidev.repository.hebergement.ReservationChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HebergementService implements IHebergementService {

    @Autowired // pour injecter des dépendances de repository ou bien (@AllArgsConstructor)
    HebergementRepository hebergementRepository;
    @Autowired
    ReservationChambreRepository reservationChambreRepository;
    @Override
    public Hebergement addHebergement(Hebergement hebergement) {
        return hebergementRepository.save(hebergement);
    }

    @Override
    public List<Hebergement> getHebergements() {
        return hebergementRepository.findAll();
    }

    @Override
    public Hebergement updateHebergement(Hebergement hebergement) {
        return hebergementRepository.save(hebergement);
    }

    @Override
    public void deleteHebergement(Long hebergement_id) {
        hebergementRepository.deleteById(hebergement_id);
    }

    @Override
    public Hebergement getHebergement(Long id) {
        return hebergementRepository.findById(id).get();
    }

    // 🔹 Affecter une réservation existante à un hébergement
    @Override
    public Hebergement affecterReservationAHebergement(Long idHebergement, Long idReservation) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement).orElseThrow();
        ReservationChambre reservation = reservationChambreRepository.findById(idReservation).orElseThrow();

        reservation.setHebergement(hebergement);
        reservationChambreRepository.save(reservation);

        return hebergement;
    }
    //  Ajouter une réservation et l’affecter à un hébergement
    @Override
    public ReservationChambre ajouterReservationEtAffecter(Long idHebergement, ReservationChambre reservation) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement).orElseThrow();

        reservation.setHebergement(hebergement);
        return reservationChambreRepository.save(reservation);
    }


    // 🔹 Affecter plusieurs réservations à un hébergement
    @Override
    public Hebergement affecterReservationsAHebergement(Long idHebergement, List<Long> idReservations) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement).orElseThrow();
        List<ReservationChambre> reservations = reservationChambreRepository.findAllById(idReservations);

        for (ReservationChambre reservation : reservations) {
            reservation.setHebergement(hebergement);
        }
        reservationChambreRepository.saveAll(reservations);

        return hebergement;
    }
}
