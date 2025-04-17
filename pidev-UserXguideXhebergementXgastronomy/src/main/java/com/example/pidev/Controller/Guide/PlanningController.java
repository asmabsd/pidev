package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.Planning;
import com.example.pidev.service.GUIDE.IPlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Planning")
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


    @GetMapping("/getOne/{idPlanning}")

    public Planning getPlanning(@PathVariable int idPlanning) {
        return PlanningService.getPlanning(idPlanning);
    }




}
