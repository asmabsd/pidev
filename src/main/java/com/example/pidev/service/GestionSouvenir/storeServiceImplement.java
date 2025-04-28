package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.entity.GestionSouvenir.StoreStatus;
import com.example.pidev.repository.GestionSouvenir.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Store> getInvalidStores() {
        return storeRepository.findByStatus(StoreStatus.LOADING);
    }

    @Override
    public Store updateStoreStatus(Long storeId) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            // Logique pour changer le statut
            if (store.getStatus() == StoreStatus.LOADING) {
                store.setStatus(StoreStatus.VALIDE); // Exemple de changement de statut
            } else if (store.getStatus() == StoreStatus.VALIDE) {
                store.setStatus(StoreStatus.LOADING); // Changer à NONVALIDE
            }
            // Sauvegarder le magasin mis à jour
            return storeRepository.save(store);
        } else {
            throw new RuntimeException("Store not found with id: " + storeId);
        }
    }

    @Override
    public List<Store> getValidStores() {
        return storeRepository.findByStatus(StoreStatus.VALIDE);
    }
}
