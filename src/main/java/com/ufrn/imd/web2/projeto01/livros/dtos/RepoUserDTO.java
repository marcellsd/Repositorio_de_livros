package com.ufrn.imd.web2.projeto01.livros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoUserDTO {
    private String username;
    private String password;
    private Boolean isAuthor;
    private Boolean isPublisher;
    private Boolean isBookstore;
    private FavoriteDTO favorite;
    
}
