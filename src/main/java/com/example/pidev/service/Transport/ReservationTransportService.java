package com.example.pidev.service.Transport;

import com.example.pidev.entity.Transport.ReservationTransport;
import com.example.pidev.entity.Transport.Transport;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.Transport.ReservationTransportRepository;
import com.example.pidev.repository.Transport.TransportRepository;
import com.example.pidev.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class ReservationTransportService {
    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationTransportRepository reservationRepository;




    public List<ReservationTransport> getAll() {
        return reservationRepository.findAll();
    }

    public ReservationTransport getById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }


    public ReservationTransport save(ReservationTransport reservation) {
        return reservationRepository.save(reservation);
    }


    public void delete(Integer id) {
        reservationRepository.deleteById(id);
    }

    public ReservationTransport update(Integer id, ReservationTransport updatedReservation) {
        updatedReservation.setId(id);
        return reservationRepository.save(updatedReservation);
    }
}

