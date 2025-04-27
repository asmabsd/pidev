package com.example.pidev.Controller.activities;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.Reservation;
import com.example.pidev.service.User.UserService;
import com.example.pidev.service.activities.ActivityService;
import com.example.pidev.service.activities.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200")

public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    // Obtenir toutes les réservations
    @GetMapping("/getAllReservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Obtenir une réservation par son id
    @GetMapping("/getReservation/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(reservation -> new ResponseEntity<>(reservation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Ajouter une réservation
    @PostMapping("/addReservation")
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.saveReservation(reservation);
    }

    // Mettre à jour une réservation
    @PutMapping("/updateReservation")
    public Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    // Supprimer une réservation
    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

    // Obtenir les réservations par l'id de l'utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(reservationService.getReservationsByUserId(userId), HttpStatus.OK);
    }

    // Obtenir les réservations par l'id de l'activité
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Reservation>> getReservationsByActivityId(@PathVariable Long activityId) {
        return new ResponseEntity<>(reservationService.getReservationsByActivityId(activityId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservationRequest) {
        try {
            // Get the user id from the request
            Long userId = reservationRequest.getUser().getId();

            // Fetch the complete user object with role
            User user = userService.getUser(userId);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            // Get the activity id from the request
            Long activityId = reservationRequest.getActivity().getIdActivity();

            // Fetch the complete activity object
            Activity activity = activityService.getActivityById(activityId).get();
            if (activity == null) {
                return new ResponseEntity<>("Activity not found", HttpStatus.NOT_FOUND);
            }

            // Set the fully loaded objects on the reservation
            reservationRequest.setUser(user);
            reservationRequest.setActivity(activity);

            // Save the reservation
            Reservation savedReservation = reservationService.saveReservation(reservationRequest);

            // Create a DTO for response to avoid cyclic references
            Map<String, Object> response = new HashMap<>();
            response.put("idReservation", savedReservation.getIdReservation());
            response.put("numberOfPeople", savedReservation.getNumberOfPeople());
            response.put("dateReservation", savedReservation.getDateReservation());
            response.put("userId", user.getId());
            response.put("activityId", activity.getIdActivity());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error creating reservation: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


