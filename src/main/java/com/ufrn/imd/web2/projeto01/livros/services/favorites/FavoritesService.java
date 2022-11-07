package com.ufrn.imd.web2.projeto01.livros.services.favorites;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.FavoriteDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Favorites;

@Service
public interface FavoritesService {
    public Favorites saveFavorites(FavoriteDTO favoriteDTO);
    public Favorites updatePatchFavoritesById(Integer id,FavoriteDTO favoriteDTO);
    public void deleteFavoriteById(Integer id);
    public Favorites getFavoriteById(Integer id);
    public Favorites convertFavoritesFromDTO(FavoriteDTO favoriteDTO);
    public List<Favorites> getFavorites();
}
