package com.example.pidev.Controller.User;

import com.example.pidev.service.User.UserStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("http://localhost:4200")
public class StatisticsController {

    private final UserStatisticsService statsService;

    public StatisticsController(UserStatisticsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(statsService.getDashboardStatistics());
    }

    @GetMapping("/users/summary")
    public ResponseEntity<?> getUserSummary() {
        return ResponseEntity.ok(statsService.getDashboardStatistics().get("userCounts"));
    }

    @GetMapping("/users/trend")
    public ResponseEntity<?> getUserTrend() {
        return ResponseEntity.ok(statsService.getDashboardStatistics().get("registrationTrend"));
    }
}