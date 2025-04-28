package com.example.pidev.repository.Gastronomy;

import com.example.pidev.entity.Gastronomy.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByGastronomyId(int gastronomyId);

}
