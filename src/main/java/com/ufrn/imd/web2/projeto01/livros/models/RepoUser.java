package com.ufrn.imd.web2.projeto01.livros.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "users")
public class RepoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15)
    @NotEmpty(message = "Campo username é obrigatório")
    private String username;

    @Column
    @NotEmpty(message = "Campo password é obrigatório")
    private String password;

    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "favorite_id")
    private Favorites favorites;

    @Column
    private Boolean isAuthor;

    @Column
    private Boolean isPublisher;

    public RepoUser(){}
    
    public RepoUser(String username, String password, Boolean isAuthor, Boolean isPublisher){
        this.username = username;
        this.password = password;
        this.isAuthor = isAuthor;
        this.isPublisher = isPublisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(Boolean isAuthor) {
        this.isAuthor = isAuthor;
    }

    public Boolean getIsPublisher() {
        return isPublisher;
    }

    public void setIsPublisher(Boolean isPublisher) {
        this.isPublisher = isPublisher;
    }

    public Favorites getFavorite() {
        return favorites;
    }

    public void setFavorite(Favorites favorite) {
        this.favorites = favorite;
    }

    

}
