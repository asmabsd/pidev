package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Command;
import com.example.pidev.entity.GestionSouvenir.MonthlySales;
import com.example.pidev.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    List<Command> findByUserOrderByCreatedAtDesc(User user);

    @Query("SELECT new com.example.pidev.entity.GestionSouvenir.MonthlySales(" +
            "EXTRACT(YEAR FROM cl.command.createdAt), " +
            "EXTRACT(MONTH FROM cl.command.createdAt), " +
            "SUM(cl.unitPrice * cl.quantity) " +
            ") FROM CommandLine cl " +
            "WHERE cl.command.status = 'CONFIRMED' AND cl.souvenir.store.id = :storeId " +
            "GROUP BY EXTRACT(YEAR FROM cl.command.createdAt), EXTRACT(MONTH FROM cl.command.createdAt) " +
            "ORDER BY EXTRACT(YEAR FROM cl.command.createdAt), EXTRACT(MONTH FROM cl.command.createdAt)")
    List<MonthlySales> findMonthlySalesByStore(@Param("storeId") Long storeId);
}
