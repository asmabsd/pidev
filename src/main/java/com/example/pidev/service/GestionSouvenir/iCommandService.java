package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Command;
import com.example.pidev.entity.GestionSouvenir.Panel;

public interface iCommandService {
    Command createCommandFromPanel(Panel panel);
    Command finalizeCommand(Long commandId);
    public Command cancelCommand(Long commandId);
}
