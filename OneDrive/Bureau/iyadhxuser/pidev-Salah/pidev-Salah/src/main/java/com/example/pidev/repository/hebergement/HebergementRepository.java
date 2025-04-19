package com.example.pidev.repository.hebergement;

import com.example.pidev.entity.hebergement.Hebergement;
import com.example.pidev.entity.hebergement.TypeHebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HebergementRepository extends JpaRepository<Hebergement,Long> {

}
