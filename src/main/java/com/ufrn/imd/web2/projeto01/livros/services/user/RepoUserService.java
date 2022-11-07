package com.ufrn.imd.web2.projeto01.livros.services.user;

import java.util.List;

import com.ufrn.imd.web2.projeto01.livros.dtos.CredentialsDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.TokenDTO;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;

public interface RepoUserService {
    public RepoUser saveUser(RepoUser user);
    public RepoUser getUserById(Integer userId);
    public List<RepoUser> getRepoUsers();
    public RepoUser updateUserById(Integer id, RepoUser updatedUser);
    public TokenDTO authenticate(CredentialsDTO credentials);
}
