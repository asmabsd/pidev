package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Optional<Discount> findByCodeAndActiveTrue(String code);

    @Query("SELECT d FROM Discount d WHERE "
            + "d.active = true AND "
            + "(d.expiresAt IS NULL OR d.expiresAt > CURRENT_DATE)")
    List<Discount> findActiveDiscounts();
}
