package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Souvenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SouvenirRepository extends JpaRepository<Souvenir, Long> {
    List<Souvenir> findByStoreId(Long storeId);

}
