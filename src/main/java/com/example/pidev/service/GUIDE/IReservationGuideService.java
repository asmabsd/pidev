package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.ReservationGuide;

import java.util.List;

public interface IReservationGuideService {

    ReservationGuide addReservationGuide(ReservationGuide ReservationGuide);
    ReservationGuide updateReservationGuide(ReservationGuide ReservationGuide  );

    void deleteReservationGuide(int idReservationGuide);
    List<ReservationGuide> getAllReservationGuide();
    ReservationGuide getReservationGuide(int idReservationGuide);

    void updateStatus(int id, String status);
}