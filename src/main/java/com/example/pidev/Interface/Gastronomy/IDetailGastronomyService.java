package com.example.pidev.Interface.Gastronomy;

import com.example.pidev.entity.Gastronomy.DetailGastronomy;

import java.util.List;

public interface IDetailGastronomyService {
    DetailGastronomy addDetailGastronomy(DetailGastronomy detailGastronomy);
    DetailGastronomy updateDetailGastronomy(DetailGastronomy detailGastronomy);
    void deleteDetailGastronomy(int id);
    List<DetailGastronomy> retrieveAllDetailGastronomies();
    DetailGastronomy retrieveDetailGastronomy(int id);
    DetailGastronomy retrieveDetailGastronomyByGastronomyId(int gastronomyId);
    DetailGastronomy updateRating(int gastronomyId, double rating);
}
