package com.example.pidev.service.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IGastronomyService;
import com.example.pidev.entity.Gastronomy.DetailGastronomy;
import com.example.pidev.entity.Gastronomy.Gastronomy;
import com.example.pidev.entity.Gastronomy.GastronomyType;
import com.example.pidev.entity.Gastronomy.Menu;
import com.example.pidev.entity.User.User;
import com.example.pidev.repository.Gastronomy.DetailGastronomyRepository;
import com.example.pidev.repository.Gastronomy.GastronomyRepository;
import com.example.pidev.repository.Gastronomy.MenuRepository;
import com.example.pidev.repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GastronomyService implements IGastronomyService {
    private static final Logger log = LoggerFactory.getLogger(GastronomyService.class);

    @Autowired
    GastronomyRepository gastronomyRepository;
    @Autowired
    private DetailGastronomyRepository detailGastronomyRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Menu addMenu(Menu menu) {
        if (menu.getGastronomy() != null && menu.getGastronomy().getId() != 0)
            {
            Gastronomy gastronomy = gastronomyRepository.findById(menu.getGastronomy().getId()).orElse(null);
            if (gastronomy != null) {
                menu.setGastronomy(gastronomy);
            }
        }
        return menuRepository.save(menu);
    }

    @Override
    public Gastronomy addGastronomy(Gastronomy gastronomy) {
      return gastronomyRepository.save(gastronomy);
    }


    @Override
    public Gastronomy updateGastronomy(Gastronomy gastronomy) {
        return gastronomyRepository.save(gastronomy);
    }

    @Override
    public void deleteGastronomy(int id) {
        gastronomyRepository.deleteById(id);
    }

    @Override
    public List<Gastronomy> retrieveAllGastronomies() {
        return gastronomyRepository.findAllWithMenusAndDetail();
    }

    @Override
    public Gastronomy retrieveGastronomy(int id) {
        return gastronomyRepository.findByIdWithMenusAndDetail(id);
    }



    @Override
    public Gastronomy affectMenuToGastronomy(int idGastronomy, List<Integer> idMenus) {
        log.info("ID Gastronomy: {}", idGastronomy);
        log.info("ID Menus: {}", idMenus);

        Gastronomy gastronomy = gastronomyRepository.findById(idGastronomy).orElse(null);
        List<Menu> menus = menuRepository.findAllById(idMenus);

        if (gastronomy != null && !menus.isEmpty()) { // Vérifier que la liste n'est pas vide
            for (Menu menu : menus) {
                menu.setGastronomy(gastronomy);
            }
            menuRepository.saveAll(menus);
            return gastronomy;
        }
        return null;
    }


    @Override
    public DetailGastronomy addDetailGastronomyAndAffectGastronomy(DetailGastronomy detailGastronomy, int idGastronomy) {
        Gastronomy gastronomy = gastronomyRepository.findById(idGastronomy).orElse(null);
        if (gastronomy != null) {
            detailGastronomy.setGastronomy(gastronomy);
            return detailGastronomyRepository.save(detailGastronomy);
        }
        return null;
    }
    @Override
    public List<Gastronomy> searchGastronomies(String name, GastronomyType type, String location, Double minRating, String plateKeyword) {
        return gastronomyRepository.searchGastronomies(name, type, location, minRating, plateKeyword);
    }
    public Map<String, Double> getAverageRatingByLocation() {
        List<Object[]> results = gastronomyRepository.getAverageRatingByLocation();
        Map<String, Double> map = new HashMap<>();
        for (Object[] row : results) {
            String location = (String) row[0];
            Double avgRating = (Double) row[1];
            map.put(location, avgRating);
        }
        return map;
    }

    public Map<String, Double> getAverageRatingByLocationAndType() {
        List<Object[]> results = gastronomyRepository.getAverageRatingByLocationAndType();
        Map<String, Double> map = new HashMap<>();
        for (Object[] row : results) {
            String location = (String) row[0];
            String type = row[1].toString(); // GastronomyType -> string
            Double avgRating = (Double) row[2];
            map.put(location + " - " + type, avgRating);
        }
        return map;
    }

}


