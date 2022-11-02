package com.ufrn.imd.web2.projeto01.livros.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 15)
    @NotEmpty
    private String username;

    @Column
    @NotEmpty
    private String password;

    @Column
    private boolean isAuthor;

    @Column
    private boolean isPublisher;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean isAuthor) {
        this.isAuthor = isAuthor;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public void setPublisher(boolean isPublisher) {
        this.isPublisher = isPublisher;
    }

    

}
