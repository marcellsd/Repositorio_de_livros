package com.ufrn.imd.web2.projeto01.livros.models;

import java.util.List;

public class Publisher {
    private String name;
    private String hqLocation;
    private String webSite;

    private List<Book> books;

    private Integer id;
    public Publisher(String name, String hqLocation, String webSite) {
        this.name = name;
        this.hqLocation = hqLocation;
        this.webSite = webSite;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHqLocation() {
        return hqLocation;
    }
    public void setHqLocation(String hqLocation) {
        this.hqLocation = hqLocation;
    }
    public String getWebSite() {
        return webSite;
    }
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Publisher [ name=" + name + ", hqLocation=" + hqLocation + ", id=" + id 
                + ", webSite=" + webSite + "]";
    }
    
}
