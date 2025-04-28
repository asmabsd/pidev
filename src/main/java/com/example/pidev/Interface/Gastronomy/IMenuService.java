package com.example.pidev.Interface.Gastronomy;

import com.example.pidev.entity.Gastronomy.Menu;

import java.util.List;

public interface IMenuService {
    Menu addMenu(Menu menu);
    Menu updateMenu(Menu menu);
    void deleteMenu(int id);
    List<Menu> retrieveAllMenus();
    Menu retrieveMenu(int id);

    List<Menu> getMenusByGastronomy(int gastronomyId);
}
