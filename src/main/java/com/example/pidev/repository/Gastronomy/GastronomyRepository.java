package com.example.pidev.repository.Gastronomy;

import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.GastronomyType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastronomyRepository extends JpaRepository<Gastronomy, Integer> {
    @Query("SELECT DISTINCT g FROM Gastronomy g " +
            "LEFT JOIN FETCH g.menus " +
            "LEFT JOIN FETCH g.detailGastronomy " +
            "WHERE g.id = :id")
    Gastronomy findByIdWithMenusAndDetail(@Param("id") int id);
    @Query("SELECT DISTINCT g FROM Gastronomy g " +
            "LEFT JOIN FETCH g.menus " +
            "LEFT JOIN FETCH g.detailGastronomy")
    List<Gastronomy> findAllWithMenusAndDetail();

    @EntityGraph(attributePaths = {"detailGastronomy"})
    List<Gastronomy> findAll();
   @Query("SELECT g FROM Gastronomy g " +
            "LEFT JOIN g.detailGastronomy d " +
            "LEFT JOIN g.menus m " +
            "LEFT JOIN m.plates p " +
            "WHERE (:name IS NULL OR LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:type IS NULL OR g.type = :type) " +
            "AND (:location IS NULL OR LOWER(g.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
            "AND (:minRating IS NULL OR d.rating >= :minRating) " +
            "AND (:plateKeyword IS NULL OR LOWER(p.namePlate) LIKE LOWER(CONCAT('%', :plateKeyword, '%')))")
    List<Gastronomy> searchGastronomies(
            @Param("name") String name,
            @Param("type") GastronomyType type,
            @Param("location") String location,
            @Param("minRating") Double minRating,
            @Param("plateKeyword") String plateKeyword
    );

    @Query("SELECT g.type, COUNT(g) FROM Gastronomy g GROUP BY g.type")
    List<Object[]> countGastronomiesByType();

    @Query("SELECT g.location, COUNT(g) FROM Gastronomy g GROUP BY g.location")
    List<Object[]> countGastronomiesByLocation();

    @Query("SELECT g.location, AVG(d.rating) " +
            "FROM Gastronomy g JOIN g.detailGastronomy d " +
            "GROUP BY g.location")
    List<Object[]> getAverageRatingByLocation();
    @Query("SELECT g.location, g.type, AVG(d.rating) " +
            "FROM Gastronomy g JOIN g.detailGastronomy d " +
            "GROUP BY g.location, g.type")
    List<Object[]> getAverageRatingByLocationAndType();

}
