package com.example.pidev.service.Transport;

import com.example.pidev.entity.Transport.ReservationTransport;
import com.example.pidev.repository.Transport.ReservationTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationTransportService {

    @Autowired
    private ReservationTransportRepository reservationTransportRepository;

    public List<ReservationTransport> getAllReservations() {
        return reservationTransportRepository.findAll();
    }

    public Optional<ReservationTransport> getReservationById(int id) {
        return reservationTransportRepository.findById(id);
    }

    public ReservationTransport saveReservation(ReservationTransport reservation) {
        return reservationTransportRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        reservationTransportRepository.deleteById(id);
    }
}
