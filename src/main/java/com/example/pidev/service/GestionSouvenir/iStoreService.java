package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;

import java.util.List;

public interface iStoreService {
    Store addStore(Store store);
    Store updateStore(Store store);
    void deleteStore(Long idStore);
    List<Store> retrieveAllStore();
    List<Store> getInvalidStores();
    List<Store> getValidStores();
    Store retrieveStore(Long idStore);
    public Store updateStoreStatus(Long storeId);
}
