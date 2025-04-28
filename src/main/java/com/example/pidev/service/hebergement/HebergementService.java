package com.example.pidev.service.hebergement;

import com.example.pidev.Interface.hebergement.IHebergementService;
import com.example.pidev.entity.exception.ResourceNotFoundException;
import com.example.pidev.entity.hebergement.*;
import com.example.pidev.repository.hebergement.HebergementRepository;
import com.example.pidev.repository.hebergement.ReservationChambreRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public ReservationChambre ajouterReservationEtAffecter(Long idHebergement, ReservationChambre reservation) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement)
                .orElseThrow(() -> new ResourceNotFoundException("Hebergement not found"));

        // Associer l'hébergement à la réservation
        reservation.setHebergement(hebergement);

        // Récupérer le type de chambre et le nombre de chambres réservées
        TypeChambre typeChambre = reservation.getTypeChambre();
        int nombreChambresReservees = reservation.getNombreChambres(); // Assure-toi que ce champ existe

        // Décrémenter le bon type de chambre
        switch (typeChambre) {
            case single:
                if (hebergement.getTotalSingleChambres() < nombreChambresReservees) {
                    throw new IllegalStateException("Pas assez de chambres SINGLE disponibles");
                }
                hebergement.setTotalSingleChambres(hebergement.getTotalSingleChambres() - nombreChambresReservees);
                break;

            case Double:
                if (hebergement.getTotalDoubleChambres() < nombreChambresReservees) {
                    throw new IllegalStateException("Pas assez de chambres DOUBLE disponibles");
                }
                hebergement.setTotalDoubleChambres(hebergement.getTotalDoubleChambres() - nombreChambresReservees);
                break;

            case Suite:
                if (hebergement.getTotalSuiteChambres() < nombreChambresReservees) {
                    throw new IllegalStateException("Pas assez de chambres SUITE disponibles");
                }
                hebergement.setTotalSuiteChambres(hebergement.getTotalSuiteChambres() - nombreChambresReservees);
                break;

            case Deluxe:
                if (hebergement.getTotalDelexueChambres() < nombreChambresReservees) {
                    throw new IllegalStateException("Pas assez de chambres DELEXUE disponibles");
                }
                hebergement.setTotalDelexueChambres(hebergement.getTotalDelexueChambres() - nombreChambresReservees);
                break;

            default:
                throw new IllegalArgumentException("Type de chambre non reconnu");
        }

        // Sauvegarder la réservation
        reservationChambreRepository.save(reservation);

        // Ajouter à la liste et incrémenter le nombre total de réservations
        hebergement.getReservationchambres().add(reservation);
        hebergement.setNombreReservations(hebergement.getNombreReservations() + 1);

        // Sauvegarder les changements de l'hébergement
        hebergementRepository.save(hebergement);

        return reservation;
    }

    @Override
    public Hebergement rateHebergement(Long idHebergement, int rating) {
        Hebergement hebergement = hebergementRepository.findById(idHebergement)
                .orElseThrow(() -> new RuntimeException("Hébergement non trouvé"));

        // S'assurer que le rating est entre 1 et 5
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Le rating doit être entre 1 et 5");
        }

        hebergement.setRating(rating); // Si tu veux gérer une seule note par hébergement
        // ⚠️ Pour une moyenne sur plusieurs notes, il faudra gérer un champ comme "nbVotes" et "sommeNotes"

        return hebergementRepository.save(hebergement);
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





}
