package com.ufrn.imd.web2.projeto01.livros.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder(); 
    };
    
}