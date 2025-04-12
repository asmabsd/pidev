package com.example.pidev.repository.GestionSouvenir;
import com.example.pidev.entity.GestionSouvenir.Command;
import com.example.pidev.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    List<Command> findByUserOrderByCreatedAtDesc(User user);

}
