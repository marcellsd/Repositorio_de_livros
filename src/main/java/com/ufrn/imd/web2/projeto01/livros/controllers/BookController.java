package com.ufrn.imd.web2.projeto01.livros.controllers;

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
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;


@RestController
@RequestMapping("/api/book")
public class BookController {
    
    @Autowired
    @Qualifier("bookServiceImpl")
    BookService bookService;
    

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
        
        Book newBook  = bookService.saveBook(bookDTO);
        return bookService.getBookDTOById(newBook.getId());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    @PutMapping("{id}")
    public void updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        bookService.updatePutById(id, updatedBook);
    }
    
    @PatchMapping("{id}")
    public void updateBookByPatch(@PathVariable Integer id, @RequestBody BookDTO updatedBookDTO) {
       bookService.updatePatchById(id, updatedBookDTO);
    }
}

    