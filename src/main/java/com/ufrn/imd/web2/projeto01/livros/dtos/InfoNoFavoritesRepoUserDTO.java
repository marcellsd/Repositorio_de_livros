package com.ufrn.imd.web2.projeto01.livros.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoNoFavoritesRepoUserDTO {
    private Integer id;
    private String username;
    private Boolean isAuthor;
    private Boolean isPublisher;
    private Boolean isBookstore;
}
