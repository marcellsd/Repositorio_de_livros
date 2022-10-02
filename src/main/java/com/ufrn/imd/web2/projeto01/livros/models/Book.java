package com.ufrn.imd.web2.projeto01.livros.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Column(length = 100)
    private String title;

    @Column(length = 5)
    private Integer numberOfPages;

    @Column(length = 3)
    private Integer edition;

    @Column()
    private Date publicationDate;

    @Column(length = 100)
    private String isbn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name="author_book",
    joinColumns = @JoinColumn(name="book_id"),
    inverseJoinColumns = @JoinColumn(name="author_id"))
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    public Publisher publisher;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Book(){
        
    }

    public Book(String title, Integer numberOfPages, Integer edition, Date publicationDate, String isbn) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.edition = edition;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    public Integer getEdition() {
        return edition;
    }
    public void setEdition(Integer edition) {
        this.edition = edition;
    }
    public Date getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Book [title=" + title + ", edition=" + edition + ", id=" + id + ", isbn=" + isbn + ", numberOfPages=" + numberOfPages
                + ", publicationDate=" + publicationDate + "]";
    }
    
    
}
