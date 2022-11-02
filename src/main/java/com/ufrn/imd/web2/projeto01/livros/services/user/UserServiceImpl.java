package com.ufrn.imd.web2.projeto01.livros.services.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.CredentialsDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.TokenDTO;
import com.ufrn.imd.web2.projeto01.livros.models.User;
import com.ufrn.imd.web2.projeto01.livros.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public TokenDTO authenticate(CredentialsDTO credentials) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User saveUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).map(user -> {
            return user;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
    
    
}
