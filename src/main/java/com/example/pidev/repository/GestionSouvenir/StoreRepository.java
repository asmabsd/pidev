package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;
import com.example.pidev.entity.GestionSouvenir.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByStatus(StoreStatus status);
}
