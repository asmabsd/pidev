package com.example.pidev.repository.User;

import com.example.pidev.entity.User.AccountStatus;
import com.example.pidev.entity.User.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByStatus(AccountStatus accountStatus);
}
