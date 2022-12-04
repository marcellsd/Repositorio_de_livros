package com.ufrn.imd.web2.projeto01.livros.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoBookstoreDTO {
    private Integer id;
    private String name;
    private List<InfoProductDTO> products;
}
