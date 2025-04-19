package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.Planning;
import com.example.pidev.repository.GUIDE.GuideRepository;
import com.example.pidev.repository.GUIDE.PlanningRepository;
import com.example.pidev.service.GUIDE.IPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Planning")
@CrossOrigin(origins = "http://localhost:4200/")

public class PlanningController {

    @Autowired
    IPlanningService PlanningService;
    @PostMapping("/addPlanning")
    Planning addPlanning(@RequestBody Planning Planning){
        return  PlanningService.addPlanning(Planning);
    }


    @PutMapping("/updateGuide")
    Planning updatePlanning(@RequestBody Planning Planning){
        return  PlanningService.updatePlanning(Planning);
    }
    @GetMapping("/viewPlanning")
    List<Planning> afficherPlanning(){
        return  PlanningService.getAllPlanning();


    }
    @DeleteMapping("/deletePlanning/{idPlanning}")
    public void deletePlanning(@PathVariable int idPlanning) {
        PlanningService.deletePlanning(idPlanning);
    }

    @GetMapping("/getOne/{idGuide}")

    public Planning getPlanningByGuide(@PathVariable int idGuide) {
        return PlanningService.getPlanningByGuide(idGuide);
    }

    @GetMapping("/getOne/{idPlanning}")

    public Planning getPlanning(@PathVariable int idPlanning) {
        return PlanningService.getPlanning(idPlanning);
    }

    @Autowired
    GuideRepository guideRepository;
    @Autowired
    PlanningRepository planningRepository;
    @PostMapping(value = "/addguidetoProject/{idGuide}")
    public Planning addguidetoPlanning(@RequestBody Planning planning, @PathVariable int idGuide) {
        // Vérifier si le guide existe
        Guide guide = guideRepository.findById(idGuide)
                .orElseThrow(() -> new RuntimeException("Guide not found"));

        // Associer le guide au planning
        planning.setGuide(guide);

        // Sauvegarder le planning mis à jour
        return planningRepository.save(planning);
    }



}