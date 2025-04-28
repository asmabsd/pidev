package com.example.pidev.service.activities;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Reservation;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.repository.activities.ActivityRepository;
import com.example.pidev.repository.activities.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class ReservationService implements IReservation {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;

    // Lire toutes les réservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Lire une réservation par son id
    public Optional<Reservation> getReservationById(Long idReservation) {
        return reservationRepository.findById(idReservation);
    }

    // Créer une réservation
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Mettre à jour une réservation
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Supprimer une réservation
    public void deleteReservation(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }

    // Lire les réservations par l'id de l'utilisateur
    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findAllByUserId(userId);
    }

    // Lire les réservations par l'id de l'activité
    public List<Reservation> getReservationsByActivityId(Long idActivity) {
        return reservationRepository.findAllByActivity_IdActivity(idActivity);
    }

    public Reservation createReservation(Long userId, Long activityId, Integer numberOfPeople, String dateReservation) {
        // Récupérer l'utilisateur et l'activité par leur ID
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Activity> activityOptional = activityRepository.findById(activityId);

        // Vérifier si l'utilisateur et l'activité existent
        if (userOptional.isPresent() && activityOptional.isPresent()) {
            // Créer la réservation
            Reservation reservation = new Reservation();
            reservation.setUser(userOptional.get());
            reservation.setActivity(activityOptional.get());
            reservation.setNumberOfPeople(numberOfPeople);
            reservation.setDateReservation(LocalDateTime.parse(dateReservation));

            // Sauvegarder la réservation
            return reservationRepository.save(reservation);
        } else {
            // Si l'utilisateur ou l'activité n'existent pas, renvoyer null ou gérer l'erreur
            return null;
        }
    }
}


