package com.example.pidev.Controller.Guide;


import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.ReservationGuide;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.GUIDE.GuideReservationRepository;
import com.example.pidev.service.GUIDE.IReservationGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/ReservationGuide")
@CrossOrigin(origins = "http://localhost:4200/")

public class ReservationGuideController {
    @Autowired
    IReservationGuideService ReservationGuideService;
    @PostMapping("/addReservationGuide")
    @CrossOrigin(origins = "http://localhost:4200/")
    ReservationGuide addReservationGuide(@RequestBody ReservationGuide ReservationGuide){
        return  ReservationGuideService.addReservationGuide(ReservationGuide);
    }


    @PutMapping("/updateReservationGuide/{id}")
    @CrossOrigin(origins = "http://localhost:4200/")
    ReservationGuide updateReservationGuide(@PathVariable int id,@RequestBody ReservationGuide ReservationGuide){
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
    // Vérifiez que l'URL correspond à votre API Spring
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable int id,
            @RequestParam String status) {

        // Implémentez la logique de mise à jour ici
        ReservationGuideService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

}