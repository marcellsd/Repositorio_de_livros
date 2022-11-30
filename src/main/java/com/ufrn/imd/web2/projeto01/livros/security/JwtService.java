package com.ufrn.imd.web2.projeto01.livros.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}") //injetando propriedades do applications.properties
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String assignKey;

    

    public String generateToken( RepoUser repoUser ){
        long expString = Long.valueOf(expiracao);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusYears(expString);
        Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant(); 
        Date date = Date.from(instant); 

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(assignKey));

        return Jwts
                .builder()
                .setSubject(repoUser.getUsername())
                .setExpiration(date)
                .signWith(key)
                .compact();
    }

    

     private Claims getClaims( String token ) throws ExpiredJwtException { 

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(assignKey));

        return Jwts
                 .parserBuilder()
                 .setSigningKey(key)
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }
     
    public boolean validToken( String token ){
        try{
            Claims claims = (Claims) getClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data =
                    dataExpiracao.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data); //data atual nao é depois da data de expiração
        }catch (Exception e){ //se ocorrer uma exceção o token não é mais válido
            return false;
        }
    }
    

    public String getUsername(String token) throws ExpiredJwtException{
        return (String) (getClaims(token)).getSubject();
    }
}
