package com.ufrn.imd.web2.projeto01.livros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ufrn.imd.web2.projeto01.livros.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("select u from User where u.username =:username")
    User findByUsername(@Param("username") String username);
}
