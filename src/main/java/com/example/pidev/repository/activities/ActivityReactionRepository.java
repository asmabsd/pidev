package com.example.pidev.repository.activities;

import com.example.pidev.entity.User.User;
import com.example.pidev.entity.activities.Activity;
import com.example.pidev.entity.activities.ActivityReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityReactionRepository extends JpaRepository<ActivityReaction, Long> {
    Optional<ActivityReaction> findByActivityAndUser(Activity activity, User user);
}