package com.ufrn.imd.web2.projeto01.livros.services.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.repositories.BookRepository;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
        
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id).map(book -> {
            return book;
        }).orElseThrow();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
}
