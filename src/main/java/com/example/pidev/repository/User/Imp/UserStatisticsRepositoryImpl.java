package com.example.pidev.repository.User.Imp;

// src/main/java/com/example/pidev/repository/impl/UserStatisticsRepositoryImpl.java

import com.example.pidev.entity.User.User;
import com.example.pidev.repository.User.UserRepository;
import com.example.pidev.repository.User.UserStatisticsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserStatisticsRepositoryImpl implements UserStatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long countByRoleName(String roleName) {
        return entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.role.name = :roleName", Long.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

    @Override
    public Long countActiveUsers() {
        return entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.status = 'ACTIVE'", Long.class)
                .getSingleResult();
    }

    @Override
    public Long countNewUsersSince(LocalDateTime startDate) {
        return entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate", Long.class)
                .setParameter("startDate", startDate)
                .getSingleResult();
    }

    @Override
    public Long countByAuthProvider(User.AuthProvider provider) {
        return entityManager.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.authProvider = :provider", Long.class)
                .setParameter("provider", provider)
                .getSingleResult();
    }

    @Override
    public List<Object[]> countRegistrationsByMonth() {
        return entityManager.createNativeQuery("""
                SELECT DATE_FORMAT(u.created_at, '%Y-%m') as month, 
                       COUNT(*) as count 
                FROM users u 
                GROUP BY DATE_FORMAT(u.created_at, '%Y-%m') 
                ORDER BY month""")
                .getResultList();
    }
}