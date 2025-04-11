package com.example.pidev.repository.hebergement;

import com.example.pidev.entity.hebergement.ReservationChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationChambreRepository extends JpaRepository<ReservationChambre,Long> {
}
