package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.repository.GestionSouvenir.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class storeServiceImplement implements iStoreService{

    @Autowired
    private StoreRepository storeRepository;
    @Override
    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(Long idStore) {
        storeRepository.deleteById(idStore);
    }

    @Override
    public List<Store> retrieveAllStore() {
        return storeRepository.findAll();
    }

    @Override
    public Store retrieveStore(Long idStore) {
        return storeRepository.findById(idStore).get();
    }

}
