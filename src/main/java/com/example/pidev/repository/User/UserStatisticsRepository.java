package com.example.pidev.repository.User;




import com.example.pidev.entity.User.User;
import java.time.LocalDateTime;
import java.util.List;

public interface UserStatisticsRepository {
    Long countByRoleName(String roleName);
    Long countActiveUsers();
    Long countNewUsersSince(LocalDateTime startDate);
    Long countByAuthProvider(User.AuthProvider provider);
    List<Object[]> countRegistrationsByMonth();
}