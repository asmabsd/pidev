package com.example.pidev.repository.GUIDE;

import com.example.pidev.entity.GUIDE.ReservationGuide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideReservationRepository extends JpaRepository<ReservationGuide,Integer> {
}
