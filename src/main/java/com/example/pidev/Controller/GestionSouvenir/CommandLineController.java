package com.example.pidev.Controller.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.CommandLine;
import com.example.pidev.service.GestionSouvenir.iCommandLineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pannel")
public class CommandLineController {
    @Autowired
    iCommandLineService commandLineService;

    @PostMapping("/addCommandLine")
    CommandLine commandLineAdd(@RequestBody CommandLine commandLine) {
        return commandLineService.addCommandLine(commandLine);
    }

    @PutMapping("/updateCommandLine")
    CommandLine commandLineUpdate(@RequestBody CommandLine commandLine, HttpSession session) {
        return commandLineService.updateCommandLine(session, commandLine);
    }

    @DeleteMapping("/deleteCommandLine")
    void commandLineDelete(@RequestParam long id) {
        commandLineService.deleteCommandLine(id);
    }

    @GetMapping("/retrieveAllCommandLine")
    List<CommandLine> retrieveAllCommandLine() {
        return commandLineService.retrieveAllCommandLine();
    }

    @GetMapping("/retrieveCommandLine")
    CommandLine retrieveCommandLine(@RequestParam long id) {
        return commandLineService.retrieveCommandLine(id);
    }
}
