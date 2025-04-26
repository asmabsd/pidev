package com.example.pidev.repository.activities;

import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.CategoryA;
import com.example.pidev.entity.activities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByUserId(Long partnerId);

    List<Activity> findByCategoryA(CategoryA categoryA);

    List<Activity> findByDisponibility(Boolean disponibility);

    List<Activity> findByPriceLessThanEqual(Integer maxPrice);
    List<Activity> findByStartDateBeforeAndActiveFalse(LocalDateTime now);


    List<Activity> findByRegion(Region region);
}
