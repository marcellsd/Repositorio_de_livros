package com.ufrn.imd.web2.projeto01.livros.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {
    private List<Integer> booksId;
    private List<Integer> authorsId; 
}
