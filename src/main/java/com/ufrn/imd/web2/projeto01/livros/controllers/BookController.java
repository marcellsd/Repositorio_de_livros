package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ufrn.imd.web2.projeto01.livros.models.Book;


@Controller
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    @Qualifier("bookServiceImpl")
    BookService bookService;
    Integer currentBookId = null;

    @RequestMapping("/getBooksList")
    public String showListaCursos(Model model){
        List<Book> books = bookService.getBooksList();
        model.addAttribute("books",books);
        return "book/bookList";
    }

    @RequestMapping("/showFormBook")
    public String showFormCurso(Model model){
        model.addAttribute("book", new Book());
        return "book/formBook";
    }

    @RequestMapping("/addBook")
    public String addBook(@ModelAttribute("book") Book book, Model model){
        Book newBook = bookService.saveBook(book);
        model.addAttribute("book", newBook);
        return "book/addBookPage";
    }
    
    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable String bookId, Model model){
        Integer id = Integer.parseInt(bookId);
        Book book =  bookService.getBookById(id);
        bookService.deletarBook(book);
        model.addAttribute("book", book);
        return "book/deleteBookPage";
    }

    @RequestMapping("/getBook/{bookId}")
    public String getBookById(@PathVariable String bookId, Model model){
        Integer id = Integer.parseInt(bookId);
        Book book =  bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book/bookPage";
    }
    
    @RequestMapping("/showFormBookUpdate/{bookId}")
    public String showFormBookUpdate(@PathVariable String BookId, @ModelAttribute("book") Book book, Model model){
        Integer id = Integer.parseInt(BookId);
        this.currentBookId = id;
        book =  bookService.getBookById(id);
        model.addAttribute("book", book);
        System.out.println(book);
        return "book/formUpdateBook";
    }

    @RequestMapping("/updateBook")
    public String updateBook(@ModelAttribute("Book") Book newBook, Model model){
        Book book = bookService.updateById(currentBookId,newBook);
        System.out.println(book);
        model.addAttribute("BookAtualizado", book);
        this.currentBookId = null;
        return "book/updateBookPage";
    }
}
