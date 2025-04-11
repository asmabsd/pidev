package com.example.pidev.service.hebergement;

import com.example.pidev.Interface.hebergement.IReservationChambreService;
import com.example.pidev.entity.exception.ResourceNotFoundException;
import com.example.pidev.entity.hebergement.ReservationChambre;
import com.example.pidev.repository.hebergement.ReservationChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReservationChambreService implements IReservationChambreService {
    @Autowired // pour injecter des dépendances de repository ou bien (@AllArgsConstructor)
    ReservationChambreRepository reservationchambreRepository;

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
        if (reservationchambreRepository.existsById(reservationchambre.getId_reservation())) { // Vérifier si l'ID existe
            return reservationchambreRepository.save(reservationchambre); // Sauvegarder la réservation mise à jour
        } else {
            throw new ResourceNotFoundException("Réservation non trouvée pour l'ID : " + reservationchambre.getId_reservation());
        }
    }
    @Override
    public void deleteReservationChambre(Long reservationchambre_id) {
        reservationchambreRepository.deleteById(reservationchambre_id);
    }

    @Override
    public ReservationChambre getReservationChambre(Long id) {
        return reservationchambreRepository.findById(id).get();
    }





}
