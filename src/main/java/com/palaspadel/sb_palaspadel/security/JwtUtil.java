
package com.palaspadel.sb_palaspadel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key signingKey;
    private final long shortExpirationMillis;
    private final long longExpirationMillis;

    public JwtUtil(
            @Value("${spring.jwt.secret}") String base64Secret,
            @Value("${spring.jwt.expiration.short}") long shortExpirationSeconds,
            @Value("${spring.jwt.expiration.long}") long longExpirationSeconds
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        try {
            this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Clave JWT inválida. Debe ser un secreto Base64 de al menos 256 bits.", e);
        }
        this.shortExpirationMillis = Duration.ofSeconds(shortExpirationSeconds).toMillis();
        this.longExpirationMillis = Duration.ofSeconds(longExpirationSeconds).toMillis();
    }

    /**
     * Genera un token JWT con los claims proporcionados y la configuración de expiración.
     * @param id
     * @param nivel
     * @param roles
     * @param stayLogged
     * @return
     */
    public String generateToken(Long id, String nivel, List<String> roles, boolean stayLogged){
        long now = System.currentTimeMillis();
        long expMillis = now + (stayLogged ? longExpirationMillis : shortExpirationMillis);
        Date issuedAt = new Date(now);
        Date expiration = new Date(expMillis);

//      Crear los claims  del token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("nivel", nivel);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Parsea el token JWT y retorna los claims (reclamaciones) contenidos en él.
     *
     * @param token el token JWT a parsear
     * @return los claims contenidos en el token
     */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Valida el token JWT verificando su firma y expiración.
     *
     * @param token el token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}