package com.ufrn.imd.web2.projeto01.livros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {
    private String username;
    private String password;
}
