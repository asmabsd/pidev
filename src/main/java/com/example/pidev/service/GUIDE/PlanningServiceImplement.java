package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.Planning;
import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.GUIDE.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlanningServiceImplement implements IPlanningService {
    @Autowired
    PlanningRepository PlanningRepo ;


    @Override
    public Planning addPlanning(Planning Planning) {
        return PlanningRepo.save(Planning);


    }

    @Override
    public Planning updatePlanning(Planning Planning) {
        return PlanningRepo.save(Planning);
    }





    @Override
    public void deletePlanning(int idPlanning) {
        PlanningRepo.deleteById(idPlanning);
    }

    @Override
    public List<Planning> getAllPlanning() {
        return PlanningRepo.findAll();
    }








    @Override
    public boolean isPlanningReserved(int guideId, LocalDateTime date) {
        // Find the guide by ID
        Optional<Guide> guideOptional = guideRepository.findById(guideId);
        if (guideOptional.isEmpty()) {
            throw new RuntimeException("Guide not found with ID: " + guideId);
        }
        Guide guide = guideOptional.get();

        // Find the planning for the guide and date
        Optional<Planning> planningOptional = PlanningRepo.findByGuideAndDate(guide, date);
        return planningOptional.map(Planning::isReserved).orElse(false);
    }
    @Override
    public Planning getPlanning(int idPlanning) {
        return PlanningRepo.findById(idPlanning).get();
    }

    @Override
    public List<Planning> getPlanningByGuide(int idGuide) {
        return PlanningRepo.findAllByGuideId(idGuide);   }



    public List<Integer> getGuideIds() {
        return PlanningRepo.findDistinctGuideIds();
    }

    public List<LocalDate> getDates() {
        return PlanningRepo.findDistinctDates();
    }

    @Autowired
    GuideRepository guideRepository;

    public Planning addguidetoPlanning( Planning planning, int idGuide){
        Guide guide= guideRepository.findById(idGuide).get();
        planning.setGuide(guide);
        return PlanningRepo.save(planning);

    }
}