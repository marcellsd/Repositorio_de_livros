package com.ufrn.imd.web2.projeto01.livros.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="address")
public class Address {
    @Column(length = 50)
    private String webSiteAddress;
    @Column(length = 50)
    private String hqAddress;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Publisher publisher;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Address(String webSiteAddress, String hqAddress, Publisher publisher) {
        this.webSiteAddress = webSiteAddress;
        this.hqAddress = hqAddress;
        this.publisher = publisher;
    }

    public Address() {}

    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    public String getHqAddress() {
        return hqAddress;
    }

    public void setHqAddress(String hqAddress) {
        this.hqAddress = hqAddress;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address [webSiteAddress=" + webSiteAddress + ", hqAddress=" + hqAddress + ", publisher=" + publisher.getName()
                + ", id=" + id + "]";
    }

  
    
    

}
