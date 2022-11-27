package com.ufrn.imd.web2.projeto01.livros.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoBookAuthorDTO {
    private Integer id;
    private String title;
    private Integer numberOfPages;
    private Integer edition;
    private Date publicationDate;
    private String isbn;
    private InfoPublisherBookDTO publisher;

}
