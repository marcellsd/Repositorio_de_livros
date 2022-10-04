package com.ufrn.imd.web2.projeto01.livros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufrn.imd.web2.projeto01.livros.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
    
}
