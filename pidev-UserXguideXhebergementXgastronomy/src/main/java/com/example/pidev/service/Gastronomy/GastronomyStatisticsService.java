package com.example.pidev.service.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IDetailGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.repository.Gastronomy.DetailGastronomyRepository;
import com.example.pidev.repository.Gastronomy.GastronomyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GastronomyStatisticsService {
    @Autowired
    private GastronomyRepository gastronomyRepository;

    @Autowired
    private DetailGastronomyRepository detailGastronomyRepository;

    public List<Object[]> getCountByType() {
        return gastronomyRepository.countGastronomiesByType();
    }

    public List<Object[]> getCountByLocation() {
        return gastronomyRepository.countGastronomiesByLocation();
    }

    public List<Object[]> getAverageRatingByType() {
        return detailGastronomyRepository.averageRatingByType();
    }
}
