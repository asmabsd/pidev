package com.example.pidev.repository.GUIDE;

import com.example.pidev.entity.GUIDE.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning,Integer> {

    Planning findByGuideId(int guideId);

}