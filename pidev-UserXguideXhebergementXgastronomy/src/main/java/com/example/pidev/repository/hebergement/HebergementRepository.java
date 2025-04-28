package com.example.pidev.repository.hebergement;

import com.example.pidev.entity.hebergement.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HebergementRepository extends JpaRepository<Hebergement,Long> {

}
