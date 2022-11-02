package com.ufrn.imd.web2.projeto01.livros.services.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.CredentialsDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.TokenDTO;
import com.ufrn.imd.web2.projeto01.livros.models.User;
import com.ufrn.imd.web2.projeto01.livros.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDTO authenticate(CredentialsDTO credentials) {
        return null;
    }

    @Override
    public User saveUser(User user) {
        String cryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptPassword);
        return userRepository.save(user);
    }


    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).map(user -> {
            return user;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Override
    public User updateUserById(Integer id, User updatedUser) {
        User user = getUserById(id);

        updatedUser.setId(user.getId());
        if(updatedUser.getIsAuthor() == null)  updatedUser.setIsAuthor(user.getIsAuthor());
        if(updatedUser.getIsPublisher() == null) updatedUser.setIsPublisher(user.getIsPublisher());
        if(updatedUser.getPassword() == null) updatedUser.setPassword(user.getPassword());
        if(updatedUser.getUsername() != null) {throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot change username");}
        else{
           updatedUser.setUsername(user.getUsername()); 
        };
        return userRepository.save(updatedUser);
    }
    
    
}
