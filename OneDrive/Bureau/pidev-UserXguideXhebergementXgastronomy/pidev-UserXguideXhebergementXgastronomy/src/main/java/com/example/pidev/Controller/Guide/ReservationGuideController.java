package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.service.GUIDE.IReservationGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/ResevationGuide")
public class ReservationGuideController {
    @Autowired
    IReservationGuideService ReservationGuideService;
    @PostMapping("/addReservationGuide")
    ReservationGuide addReservationGuide(@RequestBody ReservationGuide ReservationGuide){
        return  ReservationGuideService.addReservationGuide(ReservationGuide);
    }


    @PutMapping("/updateGuide")
    ReservationGuide updateReservationGuide(@RequestBody ReservationGuide ReservationGuide){
        return  ReservationGuideService.updateReservationGuide(ReservationGuide);
    }
    @GetMapping("/viewReservationGuide")
    List<ReservationGuide> afficherReservationGuide(){
        return  ReservationGuideService.getAllReservationGuide();


    }
    @DeleteMapping("/deleteReservationGuide/{idReservationGuide}")
    public void deleteReservationGuide(@PathVariable int idReservationGuide) {
        ReservationGuideService.deleteReservationGuide(idReservationGuide);
    }


    @GetMapping("/getOne/{idReservationGuide}")

    public ReservationGuide getReservationGuide(@PathVariable int idReservationGuide) {
        return ReservationGuideService.getReservationGuide(idReservationGuide);
    }


}
