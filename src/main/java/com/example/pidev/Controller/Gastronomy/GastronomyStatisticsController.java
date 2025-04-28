package com.example.pidev.Controller.Gastronomy;
import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.service.Gastronomy.GastronomyService;
import com.example.pidev.service.Gastronomy.GastronomyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "http://localhost:4200")

public class GastronomyStatisticsController {

    @Autowired
    private GastronomyStatisticsService statsService;
    @Autowired
    private GastronomyService gastronomyService;

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

    @GetMapping("/average-rating-by-location")
    public ResponseEntity<Map<String, Double>> getAverageRatingByLocation() {
        Map<String, Double> result = gastronomyService.getAverageRatingByLocation();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/average-rating-by-location-and-type")
    public ResponseEntity<Map<String, Double>> getAverageRatingByLocationAndType() {
        Map<String, Double> result = gastronomyService.getAverageRatingByLocationAndType();
        return ResponseEntity.ok(result);
    }

}

