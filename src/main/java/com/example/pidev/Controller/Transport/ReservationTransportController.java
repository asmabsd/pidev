package com.example.pidev.Controller.Transport;

import com.example.pidev.entity.Transport.ReservationTransport;
import com.example.pidev.service.Transport.ReservationTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationTransportController {

    @Autowired
    private ReservationTransportService reservationService;

    @GetMapping("/all")
    public List<ReservationTransport> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Optional<ReservationTransport> getReservationById(@PathVariable int id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/add")
    public ReservationTransport addReservation(@RequestBody ReservationTransport reservation) {
        return reservationService.saveReservation(reservation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }
}
