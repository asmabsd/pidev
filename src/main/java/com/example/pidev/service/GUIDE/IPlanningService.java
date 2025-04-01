package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Planning;

import java.util.List;

public interface IPlanningService {
    Planning addPlanning(Planning Planning);
    Planning updatePlanning(Planning Planning  );

    void deletePlanning(int idPlanning);
    List<Planning> getAllPlanning();
    Planning getPlanning(int idPlanning);
}
