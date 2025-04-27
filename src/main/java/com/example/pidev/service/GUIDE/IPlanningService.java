package com.example.pidev.service.GUIDE;

import com.example.pidev.entity.GUIDE.Planning;

import java.time.LocalDateTime;
import java.util.List;

public interface IPlanningService {
    Planning addPlanning(Planning Planning);
    Planning updatePlanning(Planning Planning  );

    void deletePlanning(int idPlanning);
    List<Planning> getAllPlanning();

    boolean isPlanningReserved(int guideId, LocalDateTime date);

    Planning getPlanning(int idPlanning);
    List<Planning> getPlanningByGuide(int idGuide);


    public Planning addguidetoPlanning(Planning planning, int idGuide);

}