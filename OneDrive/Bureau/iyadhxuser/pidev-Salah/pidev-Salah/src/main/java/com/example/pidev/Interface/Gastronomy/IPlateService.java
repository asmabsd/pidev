package com.example.pidev.Interface.Gastronomy;

import com.example.pidev.entity.Gastronomy.Plate;

import java.util.List;

public interface IPlateService {
    Plate addPlate(Plate plate);
    Plate updatePlate(Plate plate);
    List<Plate> getAllPlates();
    Plate getPlate(int id);
    void deletePlate(int id);

    List<Plate> getPlatesByMenuId(int menuId);
}
