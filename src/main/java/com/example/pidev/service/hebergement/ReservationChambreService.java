package com.example.pidev.service.hebergement;

import com.example.pidev.Interface.hebergement.IReservationChambreService;
import com.example.pidev.entity.exception.ResourceNotFoundException;
import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.ReservationChambre;
import com.example.pidev.entity.hebergement.TypeChambre;
import com.example.pidev.repository.hebergement.HebergementRepository;
import com.example.pidev.repository.hebergement.ReservationChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReservationChambreService implements IReservationChambreService {
    @Autowired // pour injecter des dépendances de repository ou bien (@AllArgsConstructor)
    ReservationChambreRepository reservationchambreRepository;
    @Autowired
    HebergementRepository hebergementRepository;
    @Override
    public ReservationChambre addReservationChambre(ReservationChambre reservationchambre) {
        return reservationchambreRepository.save(reservationchambre);
    }

    @Override
    public List<ReservationChambre> getReservationChambres() {
        return reservationchambreRepository.findAll();
    }

    @Override
    public ReservationChambre updateReservationChambre(ReservationChambre reservationchambre) {
        // Vérifier si la réservation existe
        ReservationChambre ancienneReservation = reservationchambreRepository.findById(reservationchambre.getId_reservation())
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée pour l'ID : " + reservationchambre.getId_reservation()));

        Hebergement hebergement = ancienneReservation.getHebergement();
        if (hebergement == null) {
            throw new IllegalStateException("Aucun hébergement associé à cette réservation");
        }

        // 🔁 Restaurer l'ancienne réservation (rendre les chambres dispo)
        TypeChambre ancienType = ancienneReservation.getTypeChambre();
        int anciennesChambres = ancienneReservation.getNombreChambres();

        switch (ancienType) {
            case single:
                hebergement.setTotalSingleChambres(hebergement.getTotalSingleChambres() + anciennesChambres);
                break;
            case Double:
                hebergement.setTotalDoubleChambres(hebergement.getTotalDoubleChambres() + anciennesChambres);
                break;
            case Suite:
                hebergement.setTotalSuiteChambres(hebergement.getTotalSuiteChambres() + anciennesChambres);
                break;
            case Deluxe:
                hebergement.setTotalDelexueChambres(hebergement.getTotalDelexueChambres() + anciennesChambres);
                break;
            default:
                throw new IllegalArgumentException("Type de chambre non reconnu (ancienne réservation)");
        }

        // 🎯 Appliquer les nouvelles contraintes (vérifier disponibilité)
        TypeChambre nouveauType = reservationchambre.getTypeChambre();
        int nouvellesChambres = reservationchambre.getNombreChambres();

        // Vérifier la disponibilité des chambres après avoir libéré les anciennes
        switch (nouveauType) {
            case single:
                if (hebergement.getTotalSingleChambres() < nouvellesChambres) {
                    throw new IllegalStateException("Pas assez de chambres SINGLE disponibles");
                }
                // Réserver les nouvelles chambres
                hebergement.setTotalSingleChambres(hebergement.getTotalSingleChambres() - nouvellesChambres);
                break;
            case Double:
                if (hebergement.getTotalDoubleChambres() < nouvellesChambres) {
                    throw new IllegalStateException("Pas assez de chambres DOUBLE disponibles");
                }
                hebergement.setTotalDoubleChambres(hebergement.getTotalDoubleChambres() - nouvellesChambres);
                break;
            case Suite:
                if (hebergement.getTotalSuiteChambres() < nouvellesChambres) {
                    throw new IllegalStateException("Pas assez de chambres SUITE disponibles");
                }
                hebergement.setTotalSuiteChambres(hebergement.getTotalSuiteChambres() - nouvellesChambres);
                break;
            case Deluxe:
                if (hebergement.getTotalDelexueChambres() < nouvellesChambres) {
                    throw new IllegalStateException("Pas assez de chambres DELUXE disponibles");
                }
                hebergement.setTotalDelexueChambres(hebergement.getTotalDelexueChambres() - nouvellesChambres);
                break;
            default:
                throw new IllegalArgumentException("Type de chambre non reconnu (nouvelle réservation)");
        }

        // Associer l'hébergement à la nouvelle réservation (au cas où il a été modifié manuellement)
        reservationchambre.setHebergement(hebergement);

        // Enregistrer les modifications
        hebergementRepository.save(hebergement);
        return reservationchambreRepository.save(reservationchambre);
    }


    @Override
    public void deleteReservationChambre(Long reservationchambre_id) {
        ReservationChambre reservation = reservationchambreRepository.findById(reservationchambre_id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        Hebergement hebergement = reservation.getHebergement();

        if (hebergement != null) {
            // Restaurer la disponibilité des chambres
            TypeChambre typeChambre = reservation.getTypeChambre();
            int nombreChambresReservees = reservation.getNombreChambres();

            switch (typeChambre) {
                case single:
                    hebergement.setTotalSingleChambres(hebergement.getTotalSingleChambres() + nombreChambresReservees);
                    break;

                case Double:
                    hebergement.setTotalDoubleChambres(hebergement.getTotalDoubleChambres() + nombreChambresReservees);
                    break;

                case Suite:
                    hebergement.setTotalSuiteChambres(hebergement.getTotalSuiteChambres() + nombreChambresReservees);
                    break;

                case Deluxe:
                    hebergement.setTotalDelexueChambres(hebergement.getTotalDelexueChambres() + nombreChambresReservees);
                    break;

                default:
                    throw new IllegalArgumentException("Type de chambre non reconnu lors de la suppression");
            }

            // Décrémenter le nombre de réservations (avec sécurité)
            hebergement.setNombreReservations(Math.max(0, hebergement.getNombreReservations() - 1));

            // Enregistrer les changements
            hebergementRepository.save(hebergement);
        }

        // Supprimer la réservation
        reservationchambreRepository.deleteById(reservationchambre_id);
    }


    @Override
    public ReservationChambre getReservationChambre(Long id) {
        return reservationchambreRepository.findById(id).get();
    }





}
