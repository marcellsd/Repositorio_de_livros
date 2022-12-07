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

import com.ufrn.imd.web2.projeto01.livros.dtos.BookstoreDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookstoreDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Bookstore;
import com.ufrn.imd.web2.projeto01.livros.services.bookstore.BookstoreService;


@RestController
@RequestMapping("/api/bookstore")
public class BookstoreController {
    @Autowired
    @Qualifier("bookstoreServiceImpl")
    BookstoreService bookstoreService;
    

    @GetMapping
    public List<InfoBookstoreDTO> getBookstoreList() {
        return bookstoreService.getBookstoresDTOList();
    }

    @GetMapping("{id}")
    public InfoBookstoreDTO getBookstoreById(@PathVariable Integer id) {
        return bookstoreService.getBookstoreDTOById(id);
    }

    @PostMapping()
    public InfoBookstoreDTO saveBookstore(@RequestBody BookstoreDTO bookstoreDTO) {
        Bookstore newBookstore = bookstoreService.saveBookstore(bookstoreDTO);
        return bookstoreService.getBookstoreDTOById(newBookstore.getId());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookstoreById(@PathVariable Integer id) {
        bookstoreService.deleteBookstoreById(id);
    }

    @PutMapping("{id}")
    public void updateBookstore(@PathVariable Integer id, @RequestBody BookstoreDTO updatedBookstoreDTO) {
        bookstoreService.updatePutById(id, updatedBookstoreDTO);
    }

    @PatchMapping("{id}")
    public void updatePublisherByPatch(@PathVariable Integer id, @RequestBody BookstoreDTO updatedBookstoreDTO) {
        bookstoreService.updatePatchById(id, updatedBookstoreDTO);
    }

}