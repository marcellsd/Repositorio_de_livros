package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.services.author.AuthorService;
@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    @Qualifier("authorServiceImpl")
    AuthorService authorService;
    Integer currentAuthorId = null;

    @RequestMapping("/getAuthorsList")
    public String showListaCursos(Model model){
        List<Author> authors = authorService.getAuthorsList();
        model.addAttribute("authors",authors);
        return "author/authorList";
    }

    @RequestMapping("/showFormAuthor")
    public String showFormCurso(Model model){
        model.addAttribute("author", new Author());
        return "author/formAuthor";
    }

    // @GetMapping("/getAuthorById/{id}")
    // public String showAuthorDetails(@PathVariable(name = "id") Integer id, Model model) {
    //     Author author = authorService.getAuthorById(id);
    //     model.addAttribute("author", author);
    //     return "author/authorPage";
    // }

    @RequestMapping("/addAuthor")
    public String addAuthor(@ModelAttribute("author") Author author, Model model){
        Author newAuthor = authorService.saveAuthor(author);
        model.addAttribute("author", newAuthor);
        return "redirect:getAuthorsList";
    }
    
    // @RequestMapping("/deleteAuthor/{authorId}")
    // public String deleteAuthor(@PathVariable String authorId, Model model){
    //     Integer id = Integer.parseInt(authorId);
    //     Author author =  authorService.getAuthorById(id);
    //     authorService.deleteAuthorById(id);
    //     model.addAttribute("author",author);
    //     return "author/deleteAuthorPage";
    // }
    
    @GetMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam(name = "id") Integer id, Model model){
        authorService.deleteAuthorById(id);
        return "redirect:getAuthorsList";
    }

    @RequestMapping("/getAuthorByID/{authorId}")
    public String getAuthorByID(@PathVariable String authorId, Model model){
        Integer id = Integer.parseInt(authorId);
        Author author =  authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "author/authorPage";
    }
    
    @RequestMapping("/showFormAuthorUpdate/{authorId}")
    public String showFormAuthorUpdate(@PathVariable String authorId, @ModelAttribute("author") Author author, Model model){
        Integer id = Integer.parseInt(authorId);
        this.currentAuthorId = id;
        author =  authorService.getAuthorById(id);
        model.addAttribute("author", author);
        System.out.println(author);
        return "author/formUpdateAuthor";
    }

    @RequestMapping("/updateAuthor")
    public String updateAuthor(@ModelAttribute("author") Author newAuthor, Model model){
        Author author = authorService.updateById(currentAuthorId,newAuthor);
        System.out.println(author);
        model.addAttribute("AuthorAtualizado", author);
        this.currentAuthorId = null;
        return "redirect:getAuthorsList";
    }
}
