package com.ufrn.imd.web2.projeto01.livros.services.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.RepoUserDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.exception.OperacaoNaoAutorizadaException;
import com.ufrn.imd.web2.projeto01.livros.models.Favorites;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.favorites.FavoritesService;


@Service
public class RepoUserServiceImpl implements UserDetailsService{
    @Autowired
    private RepoUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("favoritesServiceImpl")
    private FavoritesService favoritesService;

   


    @Transactional
    public RepoUser saveUser(RepoUserDTO userDTO) {
        RepoUser user = new RepoUser();
        String cryptPassword = passwordEncoder.encode(userDTO.getPassword());
        
        
        user.setPassword(cryptPassword);
        user.setUsername(userDTO.getUsername());
        user.setIsAuthor(userDTO.getIsAuthor());
        user.setIsPublisher(userDTO.getIsPublisher());
        
        return userRepository.save(user);
    }

    public RepoUser getUserById(Integer userId) {
        return userRepository.findById(userId).map(user -> {
            return user;
        }).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<RepoUser> getRepoUsers(){
        return userRepository.findAll();
    }

    public RepoUser updateUserById(Integer id, RepoUserDTO updatedUserDTO) {
        RepoUser user = getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RepoUser loggedUser = userRepository.findByUsername(auth.getName()).get();
        if(user.getId() != loggedUser.getId()) throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");

        RepoUser updatedUser = new RepoUser();
        updatedUser.setId(user.getId());
        if(updatedUserDTO.getIsAuthor() == null)  {
            updatedUser.setIsAuthor(user.getIsAuthor());
        }
        else {
            updatedUser.setIsAuthor(updatedUserDTO.getIsAuthor());
        }
        if(updatedUserDTO.getIsPublisher() == null) 
       { updatedUser.setIsPublisher(user.getIsPublisher());}
       else{
        updatedUser.setIsPublisher(updatedUserDTO.getIsPublisher());
       }
        if(updatedUserDTO.getPassword() == null) {user.setPassword(user.getPassword());}
        else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }
        if(updatedUserDTO.getUsername() != null) {
            throw new OperacaoNaoAutorizadaException("Username changes are not authorized");
        }
        else{
           updatedUser.setUsername(user.getUsername()); 
        };

        if(updatedUserDTO.getFavorite() == null) {
            updatedUser.setFavorite(user.getFavorite());
        }else {
            Favorites favorite = favoritesService.convertFavoritesFromDTO(updatedUserDTO.getFavorite());
            updatedUser.setFavorite(favorite);
        }
        
        return userRepository.save(updatedUser);
    }

    public UserDetails authenticate( RepoUser repoUser ){
        UserDetails user = loadUserByUsername(repoUser.getUsername());
        boolean pwdMatches = passwordEncoder.matches( repoUser.getPassword(), user.getPassword() );

        if(pwdMatches){
            return user;
        }

        throw new RuntimeException("The password does not match");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RepoUser user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
        String[] roles = {"USER"};
        if (user.getIsAuthor()) {roles = new String[] {"AUTHOR","USER"};}
        else if (user.getIsPublisher()) {roles = new String[] {"PUBLISHER","USER"};}

        return User.builder().username(user.getUsername())
                             .password(user.getPassword())
                             .roles(roles)
                             .build();
    }
    
    
}
