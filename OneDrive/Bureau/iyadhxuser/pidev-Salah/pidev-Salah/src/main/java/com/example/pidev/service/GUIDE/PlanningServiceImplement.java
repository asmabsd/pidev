package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.Planning;
import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.GUIDE.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Planning getPlanning(int idPlanning) {
        return PlanningRepo.findById(idPlanning).get();
    }

    @Override
    public Planning getPlanningByGuide(int idGuide) {
        return PlanningRepo.findByGuideId(idGuide);   }



    @Autowired
    GuideRepository guideRepository;

    public Planning addguidetoPlanning( Planning planning, int idGuide){
        Guide guide= guideRepository.findById(idGuide).get();
        planning.setGuide(guide);
        return PlanningRepo.save(planning);

    }
}