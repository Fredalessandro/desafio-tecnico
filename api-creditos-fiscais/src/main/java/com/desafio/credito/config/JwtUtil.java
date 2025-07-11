package com.desafio.credito.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
  private static final String SECRET_KEY = "umasecretkeyparajwtsegurancacomnomelogin123456";
  private static final long EXPIRATION_MS = 86400000; // 1 dia
  private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

  public String gerarToken(String login) {
    return Jwts.builder().setSubject(login).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
        .signWith(key, SignatureAlgorithm.HS256).compact();
  }

  public String extrairLogin(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
        .getSubject();
  }

  public boolean validarToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
