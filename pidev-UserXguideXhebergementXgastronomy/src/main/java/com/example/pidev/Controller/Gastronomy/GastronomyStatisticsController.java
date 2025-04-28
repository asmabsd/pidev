package com.example.pidev.Controller.Gastronomy;
import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.service.Gastronomy.GastronomyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class GastronomyStatisticsController {

    @Autowired
    private GastronomyStatisticsService statsService;

    @GetMapping("/count-by-type")
    public List<Object[]> getCountByType() {
        return statsService.getCountByType();
    }

    @GetMapping("/count-by-location")
    public List<Object[]> getCountByLocation() {
        return statsService.getCountByLocation();
    }

    @GetMapping("/average-rating-by-type")
    public List<Object[]> getAverageRatingByType() {
        return statsService.getAverageRatingByType();
    }
}

