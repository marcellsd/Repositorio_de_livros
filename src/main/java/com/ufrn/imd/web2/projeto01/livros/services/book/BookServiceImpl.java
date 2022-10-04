package com.ufrn.imd.web2.projeto01.livros.services.book;

import java.util.List;
import java.util.Optional;

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

	@Override
	public List<Book> getBooksList() {
		return bookRepository.findAll();
	}

	@Override
	public Book updateById(Integer currentBookId, Book newBook) {
            newBook.setId(currentBookId);
            return bookRepository.save(newBook);
	}

    
    
}
