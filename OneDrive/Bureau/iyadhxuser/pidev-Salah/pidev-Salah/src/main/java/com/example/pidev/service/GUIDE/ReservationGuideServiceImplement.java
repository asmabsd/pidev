package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.repository.GUIDE.GuideReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationGuideServiceImplement implements IReservationGuideService {

    @Autowired
    GuideReservationRepository ReservationGuideRepo ;


    @Override
    public ReservationGuide addReservationGuide(ReservationGuide ReservationGuide) {
        return ReservationGuideRepo.save(ReservationGuide);


    }
    public void updateStatus(int id, String status) {
        ReservationGuide reservation = ReservationGuideRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(status);
        ReservationGuideRepo.save(reservation);
    }

    @Override
    public ReservationGuide updateReservationGuide(ReservationGuide ReservationGuide) {
        return ReservationGuideRepo.save(ReservationGuide);
    }





    @Override
    public void deleteReservationGuide(int idReservationGuide) {
        ReservationGuideRepo.deleteById(idReservationGuide);
    }

    @Override
    public List<ReservationGuide> getAllReservationGuide() {
        return ReservationGuideRepo.findAll();
    }

    @Override
    public ReservationGuide getReservationGuide(int idReservationGuide) {
        return ReservationGuideRepo.findById(idReservationGuide).get();
    }
}