package com.example.pidev.Interface.Gastronomy;

import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.GastronomyType;
import com.example.pidev.entity.Gastronomy.Menu;

import java.util.List;

public interface IGastronomyService {

    Gastronomy addGastronomy(Gastronomy gastronomy);
    Gastronomy updateGastronomy(Gastronomy gastronomy);
    void deleteGastronomy(int id);
    List<Gastronomy> retrieveAllGastronomies();
    Gastronomy retrieveGastronomy(int id);
    Gastronomy affectMenuToGastronomy(int idGastronomy, List<Integer> idMenus);
    Menu addMenu(Menu menu);
    List<Gastronomy> searchGastronomies(String name, GastronomyType type, String location, Double minRating, String plateKeyword);

    DetailGastronomy addDetailGastronomyAndAffectGastronomy(DetailGastronomy detailGastronomy, int idGastronomy);

}
