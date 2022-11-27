package com.ufrn.imd.web2.projeto01.livros.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoAddressDTO {
    private Integer id;
    private String webSiteAddress;
    private String hqAddress;
    private Integer publisherId;
    private String publisherName;
}
