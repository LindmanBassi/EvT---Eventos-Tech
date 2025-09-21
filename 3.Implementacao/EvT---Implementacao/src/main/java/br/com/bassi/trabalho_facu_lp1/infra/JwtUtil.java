package br.com.bassi.trabalho_facu_lp1.infra;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final String secret = "sua-chave-secreta-bem-grande-e-segura-123!@#";
    private final Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.getBytes()));
    private final long expiracao = 1000 * 60 * 60; // 1h

    // Gerar token só com email
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiracao))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Se quiser, gerar token com role
    public String gerarToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expiracao))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validarToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            System.out.println("Token inválido: " + e.getMessage());
            return null;
        }
    }

    public String obterRole(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("role", String.class);
        } catch (JwtException e) {
            return null;
        }
    }
}
