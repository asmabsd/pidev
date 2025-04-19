package com.example.pidev.repository.Transport;

import com.example.pidev.entity.Transport.ReservationTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationTransportRepository extends JpaRepository<ReservationTransport, Integer> {
}

