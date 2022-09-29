package com.ufrn.imd.web2.projeto01.livros.services.book;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.models.Book;

@Service
public interface BookService {
    public Book saveBook(Book book);
    public Book getBookById(Integer id);
    public void deleteBookById(Integer id);
}
