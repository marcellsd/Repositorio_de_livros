package com.ufrn.imd.web2.projeto01.livros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;


public interface RepoUserRepository extends JpaRepository<RepoUser, Integer>{
    Optional<RepoUser> findByUsername(String username);
}
