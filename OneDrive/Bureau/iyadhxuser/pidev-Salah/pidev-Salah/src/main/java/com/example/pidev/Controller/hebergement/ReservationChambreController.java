package com.example.pidev.Controller.hebergement;


import com.example.pidev.Interface.hebergement.IHebergementService;
import com.example.pidev.Interface.hebergement.IReservationChambreService;
import com.example.pidev.entity.hebergement.ReservationChambre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/reservationchambre")
@CrossOrigin("http://localhost:4200")

public class ReservationChambreController {
    @Autowired
    IReservationChambreService reservationchambreService;
    @Autowired
    IHebergementService hebergementService; // Service pour récupérer l'hébergement par ID

    @PostMapping("/addreservationchambre")
    public ResponseEntity<ReservationChambre> addReservationChambre(@RequestBody ReservationChambre reservationchambre) {
        ReservationChambre savedReservation = reservationchambreService.addReservationChambre(reservationchambre);

        // Envoi de l'email de confirmation
        sendReservationConfirmationEmail(savedReservation);

        return ResponseEntity.ok(savedReservation);
    }
    // Méthode privée pour envoyer l'email de confirmation
    private void sendReservationConfirmationEmail(ReservationChambre reservation) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            String subject = "Confirmation de votre réservation #" + reservation.getId_reservation();

            String content = "Bonjour,\n\n"
                    + "Nous vous confirmons votre réservation avec les détails suivants :\n\n"
                    + "Hébergement: " + reservation.getHebergement().getName() + "\n"
                    + "Type: " + reservation.getHebergement().getType() + "\n"
                    + "Dates: du " + dateFormat.format(reservation.getDateDebut())
                    + " au " + dateFormat.format(reservation.getDateFin()) + "\n"
                    + "Nombre d'adultes: " + reservation.getNombreadulte() + "\n"
                    + "Nombre d'enfants: " + reservation.getNombrenfant() + "\n"
                    + "Prix total: " + reservation.getPrixTotal() + " DT\n\n"
                    + "Merci pour votre confiance !\n\n"
                    + "Cordialement,\nL'équipe de réservation";



        } catch (Exception e) {
            // Loggez l'erreur mais ne bloquez pas la réponse
            System.err.println("Erreur lors de l'envoi de reservation: " + e.getMessage());
        }
    }



    @PutMapping("/modifyreservationchambre/{idReservationChambre}") // Ajouter l'ID dans l'URL
    public ReservationChambre modifyReservationChambre(@PathVariable Long idReservationChambre, @RequestBody ReservationChambre reservationchambre) {
        reservationchambre.setId_reservation(idReservationChambre); // Assurez-vous que l'ID est bien défini dans l'objet
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