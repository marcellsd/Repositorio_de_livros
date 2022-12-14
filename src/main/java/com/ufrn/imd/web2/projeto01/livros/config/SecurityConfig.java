package com.ufrn.imd.web2.projeto01.livros.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ufrn.imd.web2.projeto01.livros.security.JwtAuthFilter;
import com.ufrn.imd.web2.projeto01.livros.security.JwtService;
import com.ufrn.imd.web2.projeto01.livros.services.user.RepoUserServiceImpl;


@EnableWebSecurity //configuracao de segurança
public class SecurityConfig {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private RepoUserServiceImpl usuarioService;

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Autowired 
    private Environment env;

    // @Value("${security.jwt.dev-mode}") //injetando propriedades do applications.properties
    // private String devMode;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests((authz) -> {
                try {
                    Boolean isDev = Boolean.parseBoolean(env.getProperty("security.jwt.dev-mod"));
                    if(isDev){
                        authz.antMatchers("/**") 
                        .permitAll()
                        .anyRequest().authenticated()  
                        .and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //sessões sem usuários - TODA REQUISICAO PRECISA DO TOKEN
                    .and() //volta a raiz
                        .addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);
                    }
                    else{
                        authz
                        .antMatchers(HttpMethod.GET,"/api/book/**", "/api/publisher/**", "/api/author/**", 
                                                    "/api/address/**","/api/product/**","/api/bookstore/**") 
                            .hasAnyRole("USER","PUBLISHER","AUTHOR", "BOOKSTORE")
                        .antMatchers("/api/publisher/**")
                            .hasRole("PUBLISHER")
                        .antMatchers("/api/author/**")
                            .hasRole("AUTHOR")    
                        .antMatchers("/api/book/**")
                            .hasAnyRole("AUTHOR", "PUBLISHER")
                        .antMatchers("/api/bookstore/**")
                            .hasRole("BOOKSTORE")
                        .antMatchers("/api/product/**")
                            .hasRole("BOOKSTORE")
                        .antMatchers(HttpMethod.POST, "/api/user/**")
                             .permitAll()
                        .antMatchers(HttpMethod.GET, "/v3/api-docs/**")
                             .permitAll()
                        .antMatchers(HttpMethod.GET, "/swagger-ui/**")
                             .permitAll()
                        .antMatchers(HttpMethod.GET, "/swagger-ui.html")
                             .permitAll()
                        .antMatchers("/env/**")
                             .permitAll()  
                        .anyRequest().authenticated()   
                    .and() 
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //sessões sem usuários - TODA REQUISICAO PRECISA DO TOKEN
                    .and() //volta a raiz
                        .addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
             
        return http.build();

    } 
}

