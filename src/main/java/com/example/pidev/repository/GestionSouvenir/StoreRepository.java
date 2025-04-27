package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
