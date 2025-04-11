package com.example.pidev.service.hebergement;

import com.example.pidev.Interface.hebergement.IHebergementService;
<<<<<<< HEAD
import com.example.pidev.entity.exception.ResourceNotFoundException;
=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.ReservationChambre;
import com.example.pidev.repository.hebergement.HebergementRepository;
import com.example.pidev.repository.hebergement.ReservationChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD

=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
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
<<<<<<< HEAD
    @Override
    public ReservationChambre ajouterReservationEtAffecter(Long idHebergement, ReservationChambre reservation) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement).orElseThrow(() -> new ResourceNotFoundException("Hebergement not found"));

        reservation.setHebergement(hebergement);  // Associer la réservation à l'hébergement
        reservationChambreRepository.save(reservation);  // Sauvegarder la réservation

        hebergement.getReservationchambres().add(reservation);  // Ajouter la réservation à la liste d'hébergements
        hebergementRepository.save(hebergement);  // Sauvegarder l'hébergement

        return reservation;
    }



=======
    //  Ajouter une réservation et l’affecter à un hébergement
    @Override
    public ReservationChambre ajouterReservationEtAffecter(Long idHebergement, ReservationChambre reservation) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement).orElseThrow();

        reservation.setHebergement(hebergement);
        return reservationChambreRepository.save(reservation);
    }


>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
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
<<<<<<< HEAD


    @Override
    public List<ReservationChambre> getReservationsByHebergement(Long idHebergement) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement)
                .orElseThrow(() -> new ResourceNotFoundException("Hébergement non trouvé"));

        List<ReservationChambre> allReservations = reservationChambreRepository.findAll();

        return allReservations.stream()
                .filter(reservation -> reservation.getHebergement() != null &&
                        reservation.getHebergement().getId_hebergement().equals(idHebergement))
                .toList();
    }

=======
>>>>>>> e57a0e99915b754d94251553a93aff07619f9ef2
}
