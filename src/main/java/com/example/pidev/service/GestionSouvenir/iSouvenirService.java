package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;

import java.util.List;

public interface iSouvenirService {
    Souvenir addSouvenir(Souvenir souvenir);
    Souvenir updateSouvenir(Souvenir souvenir);
    void deleteSouvenir(Long idSouvenir);
    List<Souvenir> retrieveAllSouvenir();
    Souvenir retrieveSouvenir(Long idsouvenir);
    List<Souvenir> retrieveSouvenirsByStoreId(Long storeId);

}
