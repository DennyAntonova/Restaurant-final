package com.example.Restaurant.controller;


import com.example.Restaurant.repository.MenuRepository;
import com.example.Restaurant.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import java.util.List;
import java.util.Objects;

@Controller

public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping ( "/menu-create" )
    public String showMenuForm(Menu menu, Model model) {
        model.addAttribute("menu", menu);
        return "menu-create";
    }


    @PostMapping ( "/menu-create" )
    public String addMenu(@Valid Menu menu, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                return "menu-create";
            }
            List<Menu> products = menuRepository.findAll();
            for (int i = 0; i < products.size(); i++) {
                if (Objects.equals(products.get(i).getName(), menu.getName())){
                    return "error-valid";
                }
            }
            menuRepository.save(menu);

            return "redirect:/menu-create";
        } catch (Exception e) {
            return "error-valid";
        }
    }



    @GetMapping ( "/menu-edit/{id}" )
    public String menuEdit(@PathVariable long id, Model model) {
        Menu product = menuRepository.getById(id);
        model.addAttribute("menu", product);
        return "menu-edit";
    }


    @PostMapping ( "/menu-update/{id}" )
    public String menuUpdate(@PathVariable long id, Menu menu) {
        menu.setId(id);
        menuRepository.save(menu);
        return "redirect:/menu-list";

    }

    @PostMapping ( "/menu-delete/{id}" )
    public String menuDelete(@PathVariable long id) {
        Menu menu = menuRepository.getById(id);
        menuRepository.delete(menu);
        return "redirect:/menu-list";
    }


    @GetMapping ( "/menu-list" )
    public String allMenuList(Model model) {
        List<Menu> allProducts = menuRepository.findAll();
        model.addAttribute("menu", allProducts);
        return "menu-list";
    }


    @GetMapping ( "/sort-menu" )
    public String sortMenu(Model model) {
        List<Menu> listMenu = menuRepository.findAll();
        listMenu = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "menuType"));
        model.addAttribute("listMenu", listMenu);

        return "sort-menu";
    }
}


