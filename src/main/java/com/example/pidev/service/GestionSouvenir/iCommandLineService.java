package com.example.pidev.service.GestionSouvenir;

import jakarta.servlet.http.HttpSession;
import com.example.pidev.entity.GestionSouvenir.CommandLine;

import java.util.List;

public interface iCommandLineService {
    CommandLine addCommandLine(CommandLine CommandLine);

    CommandLine updateCommandLine(HttpSession session, CommandLine CommandLine);

    void deleteCommandLine(Long idCommandLine);

    List<CommandLine> retrieveAllCommandLine();

    CommandLine retrieveCommandLine(Long idCommandLine);
}
