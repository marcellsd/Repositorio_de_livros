package com.ufrn.imd.web2.projeto01.livros.models;

import java.util.List;

public class Author {
    private String name;
    private List<Book> books;
    private Integer id;

    public Author(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Author [name=" + name + ", id=" + id + "]";
    }
    
}
