package com.example.pidev.repository.Gastronomy;

import com.example.pidev.entity.Gastronomy.Plate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlateRepository extends JpaRepository<Plate, Integer> {
    List<Plate> findByMenuId(int menuId);
}
