package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;
import com.example.pidev.repository.GestionSouvenir.SouvenirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class souvenirServiceImplement implements iSouvenirService {
@Autowired
SouvenirRepository souvenirRepository;

    @Override
    public Souvenir addSouvenir(Souvenir Souvenir) {
        return souvenirRepository.save(Souvenir);
    }

    @Override
    public Souvenir updateSouvenir(Souvenir Souvenir) {
        return souvenirRepository.save(Souvenir);
    }

    @Override
    public void deleteSouvenir(Long idSouvenir) {
         souvenirRepository.deleteById(idSouvenir);
    }

    @Override
    public List<Souvenir> retrieveAllSouvenir() {
        return souvenirRepository.findAll();
    }

    @Override
    public Souvenir retrieveSouvenir(Long idSouvenir) {
        return souvenirRepository.findById(idSouvenir)
                .orElseThrow(() -> new NoSuchElementException("Souvenir non trouvé avec ID: " + idSouvenir));
    }

    @Override
    public List<Souvenir> retrieveSouvenirsByStoreId(Long storeId) {
        return souvenirRepository.findByStoreId(storeId);
    }


}
