package com.example.pidev.Controller.Transport;

import com.example.pidev.entity.Transport.ReservationTransport;
import com.example.pidev.entity.Transport.Transport;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.Transport.ReservationTransportRepository;
import com.example.pidev.repository.Transport.TransportRepository;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.service.Transport.ReservationTransportService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationTransportController {

    @Autowired
    private final ReservationTransportService service;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationTransportRepository reservationRepository;

    @GetMapping("/all")
    public List<ReservationTransport> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationTransport> getById(@PathVariable Integer id) {
        ReservationTransport reservation = service.getById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    // ✅ C'EST CETTE MÉTHODE QUI MANQUE OU QUI EST MAL PLACÉE
    @PostMapping("/add")
    public ResponseEntity<ReservationTransport> addReservation(@RequestBody ReservationTransport reservation) {
        // Associer les entités manuellement
     //   Transport transport = transportRepository.findById(reservation.getTransport().getId())
               // .orElseThrow(() -> new RuntimeException("Transport introuvable"));

        //User user = userRepository.findById(reservation.getUser().getId())
          //      .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

       // reservation.setTransport(transport);
        //reservation.setUser(user);

        ReservationTransport saved = reservationRepository.save(reservation);

        // ✅ retour explicite de la donnée attendue par Angular
        return ResponseEntity.ok(saved);


    }


    @PutMapping("/update/{id}")
    public ReservationTransport updateReservation(@PathVariable Integer id, @RequestBody ReservationTransport reservation) {
        return service.update(id, reservation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        service.delete(id);
    }
}
