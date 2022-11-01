package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.imd.web2.projeto01.livros.dtos.BookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.services.author.AuthorService;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;
import com.ufrn.imd.web2.projeto01.livros.services.publisher.PublisherService;


@RestController
@RequestMapping("/api/book")
public class BookController {
    
    @Autowired
    @Qualifier("bookServiceImpl")
    BookService bookService;
    
    @Autowired
    @Qualifier("publisherServiceImpl")
    PublisherService publisherService;

    @Autowired
    @Qualifier("authorServiceImpl")
    AuthorService authorService;


    @GetMapping
    public List<InfoBookDTO> getBookList() {
        return bookService.getBooksDTOList();
    }

    @GetMapping("{id}")
    public InfoBookDTO getBookById(@PathVariable Integer id) {
        return bookService.getBookDTOById(id);
    }

    @PostMapping
    public InfoBookDTO saveBook(@RequestBody BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setNumberOfPages(bookDTO.getNumberOfPages());
        book.setEdition(bookDTO.getEdition());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublisher(publisherService.getPublisherById(bookDTO.getPublisherId()));
        List<Author> authors = new ArrayList<Author>();
        for (Integer authorId : bookDTO.getAuthorsId()) {
            Author author = authorService.getAuthorById(authorId);
            List<Book> books = author.getBooks();
            books.add(book);
            author.setBooks(books);
            authors.add(author);
        }
        book.setAuthors(authors);
        Book newBook  = bookService.saveBook(book);
        return bookService.getBookDTOById(newBook.getId());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    @PutMapping
    public void updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book oldBook = bookService.getBookById(id);
        updatedBook.setId(oldBook.getId());
        bookService.saveBook(updatedBook);
    }
    
    @PatchMapping("{id}")
    public void updateBookByPatch(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book oldBook = bookService.getBookById(id);
        updatedBook.setId(oldBook.getId());
        if(updatedBook.getAuthors() == null) updatedBook.setAuthors(oldBook.getAuthors());
        if(updatedBook.getEdition() == null) updatedBook.setEdition(oldBook.getEdition());
        if(updatedBook.getIsbn() == null) updatedBook.setIsbn(oldBook.getIsbn());
        if(updatedBook.getNumberOfPages() == null) updatedBook.setNumberOfPages(oldBook.getNumberOfPages());
        if(updatedBook.getPublicationDate() == null) updatedBook.setPublicationDate(oldBook.getPublicationDate());
        if(updatedBook.getPublisher() == null) updatedBook.setPublisher(oldBook.getPublisher());
        if(updatedBook.getTitle() == null) updatedBook.setTitle(oldBook.getTitle());
        bookService.saveBook(updatedBook);
    }
}

    