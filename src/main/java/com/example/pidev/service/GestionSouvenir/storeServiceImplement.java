package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.entity.GestionSouvenir.StoreStatus;
import com.example.pidev.entity.User.User;
import com.example.pidev.entity.exception.ResourceNotFoundException;
import com.example.pidev.repository.GestionSouvenir.StoreRepository;
import com.example.pidev.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class storeServiceImplement implements iStoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Store addStore(Store store) {
        if (store == null || store.getUser() == null || store.getUser().getId() == null) {
            throw new IllegalArgumentException("Store must have a valid user with an ID");
        }

        // Verify the User exists in the database
        User user = userRepository.findById(store.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + store.getUser().getId()));

        store.setUser(user); // Ensure the managed User entity is used
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) {
        if (store == null || store.getId() == null) {
            throw new IllegalArgumentException("Store must have a valid ID");
        }

        // Verify the Store exists in the database
        Store existingStore = storeRepository.findById(store.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with ID: " + store.getId()));

        // Validate the User if provided
        if (store.getUser() != null && store.getUser().getId() != null) {
            User user = userRepository.findById(store.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + store.getUser().getId()));
            store.setUser(user); // Use the managed User entity
        } else {
            // Optionally, retain the existing Store's User if not provided
            store.setUser(existingStore.getUser());
        }

        // Update fields
        existingStore.setName(store.getName());
        existingStore.setAddress(store.getAddress());
        existingStore.setDescription(store.getDescription());
        existingStore.setPhone(store.getPhone());
        existingStore.setStatus(store.getStatus());
        // Update other fields as needed

        return storeRepository.save(existingStore);
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
