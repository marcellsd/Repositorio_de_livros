package com.ufrn.imd.web2.projeto01.livros.services.user;

import com.ufrn.imd.web2.projeto01.livros.dtos.CredentialsDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.TokenDTO;
import com.ufrn.imd.web2.projeto01.livros.models.User;

public interface UserService {
    public User saveUser(User user);
    public User updatePassword(User user);
    public TokenDTO authenticate(CredentialsDTO credentials);
}
