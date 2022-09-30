package com.ufrn.imd.web2.projeto01.livros.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;

@Entity
@Table(name = "publishers")
public class Publisher {
    
    @Column(length = 50)
    private String name;

    @Column(length = 150)
    private String hqLocation;

    @Column(length = 150)
    private String webSite;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Publisher() {
        
    }

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
