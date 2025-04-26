package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.Planning;
import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.repository.GUIDE.GuideReservationRepository;
import com.example.pidev.repository.GUIDE.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationGuideServiceImplement implements IReservationGuideService {

    @Autowired
    GuideReservationRepository ReservationGuideRepo ;

    @Autowired
    PlanningRepository planningRepository ;

    @Override
    public ReservationGuide addReservationGuide(ReservationGuide ReservationGuide) {
        LocalDateTime reservationDate = ReservationGuide.getDateHour(); // Ou adaptez selon le format
        Guide guide = ReservationGuide.getGuide();

        Optional<Planning> optionalPlanning = planningRepository.findByGuideAndDate(guide, reservationDate);

        if (optionalPlanning.isPresent()) {
            Planning planning = optionalPlanning.get();
            if (planning.isReserved()) {
                throw new IllegalStateException("Ce guide est déjà réservé à cette date !");
            } else {
                planning.setReserved(true);
                planningRepository.save(planning);
            }
        } else {
            // Créer un nouveau planning si aucun n'existe
            Planning newPlanning = new Planning();
            newPlanning.setGuide(guide);
            newPlanning.setDate(reservationDate);
            newPlanning.setReserved(true);
            planningRepository.save(newPlanning);
        }

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