package com.example.pidev.repository.Gastronomy;

import com.example.pidev.entity.Gastronomy.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {}
