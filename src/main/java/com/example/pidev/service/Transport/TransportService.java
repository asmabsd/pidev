package com.example.pidev.service.Transport;

import com.example.pidev.entity.Transport.Transport;
import com.example.pidev.repository.Transport.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {
    @Autowired
    TransportRepository TransportRepository;

    public Transport addTransport(Transport transport) {
        return TransportRepository.save(transport);
    }

    public List<Transport> getTransports() {
        return TransportRepository.findAll();
    }

    public Transport updateTransport(Transport transport) {
        return TransportRepository.save(transport);
    }

    public void deleteTransport(Integer transportId) {
        TransportRepository.deleteById(transportId);
    }

    public Transport getTransport(Integer id) {
        return TransportRepository.findById(id).orElseThrow();
    }
}
