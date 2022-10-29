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

import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.services.author.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    @Qualifier("authorServiceImpl")
    AuthorService authorService;
   

    @GetMapping()
    public List<Author> showAuthorList(){
       return authorService.getAuthorsList();
    }

    @PostMapping
    public Author saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }


    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping("{id}")
    public Author getAuthorByid(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }
    
    @PutMapping("{id}")
    public void updateAuthor(@PathVariable Integer id, @RequestBody Author updatedAuthor) {
        Author oldAuthor = authorService.getAuthorById(id);
        updatedAuthor.setId(oldAuthor.getId());
        authorService.saveAuthor(updatedAuthor);
    }

    @PatchMapping("{id}")
    public void updateAuthorByPatch(@PathVariable Integer id, @RequestBody Author updatedAuthor) {
        Author oldAuthor = authorService.getAuthorById(id);
        updatedAuthor.setId(oldAuthor.getId());
        if(updatedAuthor.getBooks() == null) updatedAuthor.setBooks(oldAuthor.getBooks());
        if(updatedAuthor.getName() == null) updatedAuthor.setName(oldAuthor.getName());
        authorService.saveAuthor(updatedAuthor);
    }
}
