package com.ufrn.imd.web2.projeto01.livros.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.imd.web2.projeto01.livros.models.Favorites;
import com.ufrn.imd.web2.projeto01.livros.services.favorites.FavoritesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    @Autowired
    @Qualifier("favoritesServiceImpl")
    private FavoritesService favoritesService;
    
    @GetMapping
    public List<Favorites> getFavorites() {
        return favoritesService.getFavorites();
    }
    
}
