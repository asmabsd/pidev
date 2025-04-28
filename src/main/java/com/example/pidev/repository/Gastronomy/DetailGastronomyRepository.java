package com.example.pidev.repository.Gastronomy;

import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailGastronomyRepository extends JpaRepository<DetailGastronomy, Integer> {
    @Query("SELECT d.type, AVG(d.rating) FROM DetailGastronomy d GROUP BY d.type")
    List<Object[]> averageRatingByType();

    DetailGastronomy findByGastronomyId(int gastronomyId);

}
