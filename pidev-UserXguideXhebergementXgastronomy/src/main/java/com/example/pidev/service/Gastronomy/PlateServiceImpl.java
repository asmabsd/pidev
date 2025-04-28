package com.example.pidev.service.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IPlateService;
import com.example.pidev.entity.Gastronomy.Plate;
import com.example.pidev.repository.Gastronomy.PlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateServiceImpl implements IPlateService {

    @Autowired
    private PlateRepository plateRepository;

    @Override
    public Plate addPlate(Plate plate) {
        return plateRepository.save(plate);
    }

    @Override
    public Plate updatePlate(Plate plate) {
        return plateRepository.save(plate);
    }

    @Override
    public List<Plate> getAllPlates() {
        return plateRepository.findAll();
    }

    @Override
    public Plate getPlate(int id) {
        return plateRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePlate(int id) {
        plateRepository.deleteById(id);
    }
    @Override
    public List<Plate> getPlatesByMenuId(int menuId) {
        return plateRepository.findByMenuId(menuId);
    }

}
