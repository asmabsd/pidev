package com.example.pidev.service.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IMenuService;
import com.example.pidev.entity.Gastronomy.Menu;
import com.example.pidev.repository.Gastronomy.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService implements IMenuService {
    @Autowired
    MenuRepository menuRepository;

    @Override
    public Menu addMenu(Menu menu) {
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
}
