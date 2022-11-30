package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.CredentialsDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.FavoriteDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoNoFavoritesRepoUserDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoRepoUserDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.TokenDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.SenhaInvalidaException;
import com.ufrn.imd.web2.projeto01.livros.models.Favorites;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.security.JwtService;
import com.ufrn.imd.web2.projeto01.livros.services.favorites.FavoritesService;
import com.ufrn.imd.web2.projeto01.livros.services.user.RepoUserServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final RepoUserServiceImpl userService;
    private final JwtService jwtService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepoUser saveUser(@RequestBody InfoRepoUserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RepoUser updateUser(@PathVariable Integer id, @RequestBody InfoRepoUserDTO updatedUserDTO) {
        return userService.updateUserById(id, updatedUserDTO);
    }

    @GetMapping
    public List<RepoUser> getRepoUsers() {
        return userService.getRepoUsers();
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials) throws UsernameNotFoundException{
        try{
            RepoUser user = new RepoUser(credentials.getUsername(), credentials.getPassword(), null, null);
            userService.authenticate(user);
            String token = jwtService.generateToken(user);
            return new TokenDTO(user.getUsername(), token);
        } catch(UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    
    @GetMapping("{id}")
    public InfoRepoUserDTO getRepoUser(@PathVariable Integer id) {
        return userService.getFavoritesDTO(id);
    }

    @PostMapping("{id}/favorite")
    @ResponseStatus(HttpStatus.OK)
    public InfoRepoUserDTO setFavorite(@PathVariable Integer id, @RequestBody FavoriteDTO favoritesDTO){
        userService.saveFavorite(id, favoritesDTO);
        return userService.getFavoritesDTO(id);
    }
   
    @GetMapping("getByUsername")
    public InfoNoFavoritesRepoUserDTO getUserByUsername(@RequestBody String username){
        return userService.getRepoUserByUsername(username);
    }

}
