package com.example.pidev.service.User;

import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.repository.User.UserStatisticsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatisticsService {

    private final UserStatisticsRepository userStatsRepo;

    public UserStatisticsService(UserStatisticsRepository userStatsRepo) {
        this.userStatsRepo = userStatsRepo;
    }

    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

        // User Counts
        stats.put("userCounts", Map.of(
                "total", userStatsRepo.countByRoleName(null), // Implement a total count method if needed
                "guides", userStatsRepo.countByRoleName("GUIDE"),
                "travelers", userStatsRepo.countByRoleName("USER"),
                "partners", userStatsRepo.countByRoleName("PARTNER"),
                "newThisMonth", userStatsRepo.countNewUsersSince(monthStart)
        ));

        // Status Overview
        stats.put("statusOverview", Map.of(
                "active", userStatsRepo.countActiveUsers(),
                "providers", Map.of(
                        "local", userStatsRepo.countByAuthProvider(User.AuthProvider.LOCAL),
                        "google", userStatsRepo.countByAuthProvider(User.AuthProvider.GOOGLE)
                )
        ));

        // Registration Trend
        stats.put("registrationTrend", userStatsRepo.countRegistrationsByMonth());

        return stats;
    }
}