package com.ufrn.imd.web2.projeto01.livros.services.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.exception.OperacaoNaoAutorizadaException;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;


@Service
public class RepoUserServiceImpl implements UserDetailsService{
    @Autowired
    private RepoUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public RepoUser saveUser(RepoUser user) {
        String cryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptPassword);
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

    public RepoUser updateUserById(Integer id, RepoUser updatedUser) {
        RepoUser user = getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RepoUser loggedUser = userRepository.findByUsername(auth.getName()).get();
        if(user.getId() != loggedUser.getId()) throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");

        updatedUser.setId(user.getId());
        if(updatedUser.getIsAuthor() == null)  updatedUser.setIsAuthor(user.getIsAuthor());
        if(updatedUser.getIsPublisher() == null) updatedUser.setIsPublisher(user.getIsPublisher());
        if(updatedUser.getPassword() == null) {updatedUser.setPassword(user.getPassword());}
        if(updatedUser.getUsername() != null) {
            throw new OperacaoNaoAutorizadaException("Username changes are not authorized");
        }
        else{
           updatedUser.setUsername(user.getUsername()); 
        };
        
        return saveUser(updatedUser);
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
