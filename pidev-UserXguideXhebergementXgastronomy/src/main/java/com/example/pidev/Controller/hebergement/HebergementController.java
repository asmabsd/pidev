package com.example.pidev.Controller.hebergement;

import com.example.pidev.Interface.hebergement.IHebergementService;
import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.ReservationChambre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hebergement")
public class HebergementController {

    @Autowired
    IHebergementService hebergementService;

    @PostMapping("/addhebergement")  // http://localhost:8089/tourisme/hebergement/addhebergement
    public Hebergement addHebergement(@RequestBody Hebergement h)
    {
        return hebergementService.addHebergement(h);
    }

    @PutMapping("/modifyhebergement") // http://localhost:8089/tourisme/hebergement/modifyhebergement
    public Hebergement modifyHebergement(@RequestBody Hebergement h) {
        return hebergementService.updateHebergement(h);
    }

    @DeleteMapping("/removehebergement/{hebergement-id}") // http://localhost:8089/tourisme/hebergement/removehebergement/{hebergement-id}
    public void removeHebergement(@PathVariable("hebergement-id") Long hId) {
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

    //  Ajouter une réservation et l’affecter à un hébergement
    @PostMapping("/ajouterReservation/{idHebergement}")
    public ReservationChambre ajouterReservationEtAffecter(
            @PathVariable Long idHebergement,
            @RequestBody ReservationChambre reservation) {
        return hebergementService.ajouterReservationEtAffecter(idHebergement, reservation);
    }

    // 📌 Affecter plusieurs réservations à un hébergement
    @PutMapping("/affecterReservations/{idHebergement}")
    public Hebergement affecterReservationsAHebergement(
            @PathVariable Long idHebergement,
            @RequestBody List<Long> idReservations) {
        return hebergementService.affecterReservationsAHebergement(idHebergement, idReservations);
    }

}