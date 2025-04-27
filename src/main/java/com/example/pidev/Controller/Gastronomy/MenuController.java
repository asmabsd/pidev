package com.example.pidev.Controller.Gastronomy;

import com.example.pidev.Interface.Gastronomy.IMenuService;
import com.example.pidev.entity.Gastronomy.Menu;
import com.example.pidev.entity.Gastronomy.Plate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    IMenuService menuService;

    @PostMapping("/addMenu")
    public Menu addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @PutMapping("/updateMenu")
    public Menu updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @GetMapping("/retrieveAllMenus")
    public List<Menu> retrieveAllMenus() {
        return menuService.retrieveAllMenus();
    }

    @GetMapping("/retrieveMenu/{id}")
    public Menu retrieveMenu(@PathVariable int id) {
        return menuService.retrieveMenu(id);
    }

    @DeleteMapping("/deleteMenu/{id}")
    public void deleteMenu(@PathVariable int id) {
        menuService.deleteMenu(id);
    }

    @GetMapping("/getMenusByGastronomy/{gastronomyId}")
    public List<Menu> getMenusByGastronomy(@PathVariable int gastronomyId) {
        return menuService.getMenusByGastronomy(gastronomyId);
    }

}
