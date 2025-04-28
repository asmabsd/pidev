package com.example.pidev.repository.activities;

import com.example.pidev.entity.activities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Trouver les réservations par activité
    List<Reservation> findAllByActivity_IdActivity(Long activityId);

    // Trouver les réservations par utilisateur
    List<Reservation> findAllByUserId(Long userId);
}




