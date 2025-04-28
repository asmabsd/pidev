package com.example.pidev.service.activities;

import com.example.pidev.entity.activities.Reservation;
import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.User.User;

import java.util.List;
import java.util.Optional;

public interface IReservation {

    // Méthode pour récupérer toutes les réservations
    List<Reservation> getAllReservations();

    // Méthode pour récupérer une réservation par son ID
    Optional<Reservation> getReservationById(Long idReservation);

    // Méthode pour ajouter une nouvelle réservation
    Reservation saveReservation(Reservation reservation);

    // Méthode pour mettre à jour une réservation existante
    Reservation updateReservation(Reservation reservation);

    // Méthode pour supprimer une réservation par son ID
    void deleteReservation(Long idReservation);

    // Méthode pour récupérer les réservations d'un utilisateur
    List<Reservation> getReservationsByUserId(Long userId);

    // Méthode pour récupérer les réservations d'une activité spécifique
    List<Reservation> getReservationsByActivityId(Long activityId);

    // Méthode pour récupérer les réservations selon la date
}
