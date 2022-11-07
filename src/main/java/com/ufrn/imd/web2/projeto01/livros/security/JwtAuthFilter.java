package com.ufrn.imd.web2.projeto01.livros.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ufrn.imd.web2.projeto01.livros.services.user.RepoUserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private RepoUserServiceImpl userService;

    public JwtAuthFilter( JwtService jwtService, RepoUserServiceImpl userService ) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if( authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.validToken(token);

            if(isValid){
                String loginUsuario = jwtService.getUsername(token);
                UserDetails usuario = userService.loadUserByUsername(loginUsuario);
               
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities());
                      
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
