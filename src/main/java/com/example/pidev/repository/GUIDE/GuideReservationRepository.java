package com.example.pidev.repository.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuideReservationRepository extends JpaRepository<ReservationGuide,Integer> {
     //ReservationGuide findByGuideIdAndStatus(Long guideId, String accepted) ;

    List<ReservationGuide> findByGuide(Guide guide);
    ReservationGuide findByGuideIdAndStatus(int guideId, String status);
    ReservationGuide findTopByGuideAndStatusOrderByDateHourDesc(Guide guide,String p );


    List<ReservationGuide> findByGuideAndStatus(Guide guide, String accepted);

    List<ReservationGuide> findByUser(Optional<User> user);
}