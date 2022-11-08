package com.ufrn.imd.web2.projeto01.livros.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoFavoritesDTO {
    List<InfoBookDTO> books;
    List<InfoAuthorDTO> authors;
}
