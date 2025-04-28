package com.example.pidev.service.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IMenuService;
import com.example.pidev.entity.Gastronomy.Menu;
import com.example.pidev.entity.Gastronomy.Plate;
import com.example.pidev.repository.Gastronomy.MenuRepository;
import com.example.pidev.repository.Gastronomy.PlateRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService implements IMenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private PlateRepository plateRepository;
    @Override
    public Menu addMenu(Menu menu) {
        if (menu.getPlates() != null) {
            menu.getPlates().forEach(plate -> plate.setMenu(menu));
        }

        return menuRepository.save(menu);
    }


    @Override
    public Menu updateMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(int id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<Menu> retrieveAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu retrieveMenu(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public List<Menu> getMenusByGastronomy(int gastronomyId) {
        return menuRepository.findByGastronomyId(gastronomyId);
    }

}
