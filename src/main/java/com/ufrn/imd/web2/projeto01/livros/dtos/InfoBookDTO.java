package com.ufrn.imd.web2.projeto01.livros.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoBookDTO {
    private Integer id;
    private String title;
    private Integer numberOfPages;
    private Integer edition;
    private Date publicationDate;
    private String isbn;
    private List<InfoAuthorBookDTO> authors;
    private InfoPublisherBookDTO publisher;

}
