package com.example.pidev.Controller.hebergement;


import com.example.pidev.Interface.hebergement.IReservationChambreService;
import com.example.pidev.entity.hebergement.ReservationChambre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservationchambre")
public class ReservationChambreController {
    @Autowired
    IReservationChambreService reservationchambreService;

    @PostMapping("/addreservationchambre")  // http://localhost:8089/tourisme/reservationchambre/addreservationchambre
    public ReservationChambre addReservationChambre(@RequestBody ReservationChambre reservationchambre)
    {
        return reservationchambreService.addReservationChambre(reservationchambre);
    }

    @PutMapping("/modifyreservationchambre") // http://localhost:8089/tourisme/reservationchambre/modifyreservationchambre
    public ReservationChambre modifyReservationChambre(@RequestBody ReservationChambre reservationchambre) {
        return reservationchambreService.updateReservationChambre(reservationchambre);
    }

    @DeleteMapping("/removereservationchambre/{reservationchambre-id}") // http://localhost:8089/tourisme/reservationchambre/removereservationchambre/{reservationchambre-id}
    public void removeReservationChambre(@PathVariable("reservationchambre-id") Long reservationchambreId) {
        reservationchambreService.deleteReservationChambre(reservationchambreId);
    }

    @GetMapping("/getallr")              //http://localhost:8089/tourisme/reservationchambre/getallr
    public List<ReservationChambre> getAllReservationChambre() {
        return reservationchambreService.getReservationChambres(); // Assurez-vous que cette méthode retourne bien une List<ReservationChambre>
    }

    @GetMapping("/getonereservationchambre/{idReservationChambre}")       //http://localhost:8089/tourisme/reservationchambre/getonereservationchambre/{idReservationChambre}
    public ReservationChambre getOneReservationChambre(@PathVariable long idReservationChambre)
    {
        return reservationchambreService.getReservationChambre(idReservationChambre);
    }
}