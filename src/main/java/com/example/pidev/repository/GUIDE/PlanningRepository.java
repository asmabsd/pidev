package com.example.pidev.repository.GUIDE;

import com.example.pidev.entity.GUIDE.Guide;
import com.example.pidev.entity.GUIDE.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlanningRepository extends JpaRepository<Planning,Integer> {
    @Query("SELECT DISTINCT p.guide FROM Planning p")
    List<Integer> findDistinctGuideIds();

    @Query("SELECT DISTINCT p.date FROM Planning p")
    List<java.time.LocalDate> findDistinctDates();
    List<Planning> findAllByGuideId(int guideId);
   Optional<Planning> findByGuideAndDate(Guide guide, LocalDateTime date);
    @Query("SELECT p FROM Planning p WHERE p.guide = :guide AND p.date BETWEEN :start AND :end")
    Optional<Planning> findPlanningInDateRange(@Param("guide") Guide guide,
                                               @Param("start") LocalDateTime start,
                                               @Param("end") LocalDateTime end);


   // @Query("SELECT p FROM Planning p WHERE p.guide = :guide AND p.date = :date")
    //Optional<Planning> findByGuideAndDate(@Param("guide") Guide guide, @Param("date") LocalDateTime date);
}