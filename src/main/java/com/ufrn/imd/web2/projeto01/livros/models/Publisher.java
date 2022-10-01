package com.ufrn.imd.web2.projeto01.livros.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "publishers")
public class Publisher {
    
    @Column(length = 50)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "publisher_address",
    joinColumns = @JoinColumn(name="publisher_id"),
    inverseJoinColumns = @JoinColumn(name="address_id"))
    private Address address;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Publisher() {
        
    }

    public Publisher(String name, Address address) {
        this.name = name;
        this.address = address;
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        return "Publisher [ name=" + name + ", hqLocation=" + this.address.getHqAddress() + ", id=" + id 
                + ", webSite=" + this.address.getWebSiteAddress() + "]";
    }
    
}
