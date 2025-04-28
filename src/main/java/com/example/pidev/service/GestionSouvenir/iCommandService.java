package com.example.pidev.service.GestionSouvenir;

import com.example.pidev.entity.GestionSouvenir.Command;
import com.example.pidev.entity.GestionSouvenir.MonthlySales;
import com.example.pidev.entity.GestionSouvenir.Panel;

import java.util.List;

public interface iCommandService {
    Command createCommandFromPanel(Panel panel);
    Command finalizeCommand(Long commandId);
    public Command cancelCommand(Long commandId);
    List<MonthlySales> findMonthlySales(Long storeId);
}
