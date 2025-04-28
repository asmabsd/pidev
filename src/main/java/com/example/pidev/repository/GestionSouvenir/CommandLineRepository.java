package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.entity.GestionSouvenir.TopSellingSouvenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommandLineRepository extends JpaRepository<CommandLine, Long> {
    List<CommandLine> findByCommandId(Long commandId);

    @Query("SELECT new com.example.pidev.entity.GestionSouvenir.TopSellingSouvenir(" +
            "cl.souvenir, " +
            "SUM(cl.quantity), " +
            "SUM(cl.unitPrice * cl.quantity) " +
            ") FROM CommandLine cl " +
            "WHERE cl.command.status = 'CONFIRMED' AND cl.souvenir.store.id = :storeId " +
            "GROUP BY cl.souvenir " +
            "ORDER BY SUM(cl.quantity) DESC")
    List<TopSellingSouvenir> findTopSellingSouvenirsByStore(@Param("storeId") Long storeId);

    @Query("SELECT cl FROM CommandLine cl JOIN FETCH cl.command JOIN FETCH cl.souvenir " +
            "WHERE cl.command.status = 'CONFIRMED' AND cl.souvenir.store.id = :storeId")
    List<CommandLine> findConfirmedSalesByStore(@Param("storeId") Long storeId);
}
