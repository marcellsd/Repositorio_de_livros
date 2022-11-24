package com.ufrn.imd.web2.projeto01.livros.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherDTO {
    private String name;
    public String hqAddress;
    public String webSiteAddress;
}
