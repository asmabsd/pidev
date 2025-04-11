package com.example.pidev.repository.Gastronomy;

import com.example.pidev.entity.Gastronomy.Gastronomy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastronomyRepository extends JpaRepository<Gastronomy, Integer> {}
