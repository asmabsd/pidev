package com.example.pidev.repository.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommandLineRepository extends JpaRepository<CommandLine, Long> {
    List<CommandLine> findByCommandId(Long commandId);

}
