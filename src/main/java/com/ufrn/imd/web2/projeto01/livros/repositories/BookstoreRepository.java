package com.ufrn.imd.web2.projeto01.livros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufrn.imd.web2.projeto01.livros.models.Bookstore;

public interface BookstoreRepository extends JpaRepository<Bookstore,Integer>{
    
}
