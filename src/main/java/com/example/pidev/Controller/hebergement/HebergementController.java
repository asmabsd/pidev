package com.example.pidev.Controller.hebergement;

import com.example.pidev.Interface.hebergement.IHebergementService;
import com.example.pidev.entity.exception.ResourceNotFoundException;
import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.ReservationChambre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/hebergement")
@CrossOrigin("http://localhost:4200")

public class HebergementController {

    @Autowired
    IHebergementService hebergementService;

    @PostMapping("/addhebergement")  // http://localhost:8089/tourisme/hebergement/addhebergement
    public Hebergement addHebergement(@RequestBody Hebergement h)
    {
        return hebergementService.addHebergement(h);
    }
    @PutMapping("/modifyhebergement/{id}")
    public Hebergement modifyHebergement(@PathVariable Long id, @RequestBody Hebergement h) {
        h.setId_hebergement(id); // Assure-toi que l'ID soit bien défini dans l'objet
        return hebergementService.updateHebergement(h);
    }

    @DeleteMapping("/removehebergement/{hebergement-id}") // http://localhost:8089/tourisme/hebergement/removehebergement/{hebergement-id}
    public void removeHebergement(@PathVariable("hebergement-id") Long hId)
    {
        hebergementService.deleteHebergement(hId);
    }

    @GetMapping("/getallh")              //http://localhost:8089/tourisme/hebergement/getallh
    public List<Hebergement> getAllHebergement() {
        return hebergementService.getHebergements(); // Assurez-vous que cette méthode retourne bien une List<Hebergement>
    }

    @GetMapping("/getoneh/{idHebergement}")       //http://localhost:8089/tourisme/hebergement/getoneh/{{idHebergement}}
    public Hebergement getOneHebergement(@PathVariable long idHebergement)
    {
        return hebergementService.getHebergement(idHebergement);
    }

    //  Affecter une réservation existante à un hébergement
    @PutMapping("/affecterReservation/{idHebergement}/{idReservation}")
    public Hebergement affecterReservationAHebergement(
            @PathVariable Long idHebergement,
            @PathVariable Long idReservation) {
        return hebergementService.affecterReservationAHebergement(idHebergement, idReservation);
    }


    @PostMapping("/ajouterReservation/{idHebergement}")
    public ReservationChambre ajouterReservationEtAffecter(
            @PathVariable Long idHebergement,
            @RequestBody ReservationChambre reservation) {

        ReservationChambre savedReservation = hebergementService.ajouterReservationEtAffecter(idHebergement, reservation);

        // Envoi de l'email de confirmation

        return savedReservation;
    }






    // 📌 Affecter plusieurs réservations à un hébergement
    @PutMapping("/affecterReservations/{idHebergement}")
    public Hebergement affecterReservationsAHebergement(
            @PathVariable Long idHebergement,
            @RequestBody List<Long> idReservations) {
        return hebergementService.affecterReservationsAHebergement(idHebergement, idReservations);
    }


    @GetMapping("/reservations/{idHebergement}")
    public List<ReservationChambre> getReservationsByHebergement(@PathVariable Long idHebergement) {
        return hebergementService.getReservationsByHebergement(idHebergement);
    }
    @PutMapping("/rate/{idHebergement}")
    public Hebergement rateHebergement(@PathVariable Long idHebergement, @RequestParam int rating) {
        return hebergementService.rateHebergement(idHebergement, rating);
    }




}