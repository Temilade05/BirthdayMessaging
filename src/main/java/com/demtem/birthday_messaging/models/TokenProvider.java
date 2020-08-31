package com.demtem.birthday_messaging.models;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TokenProvider {


    private String tokenSecret;

    private int tokenExpiration;

    public TokenProvider (@Value("${app.auth.tokenSecret}") String tokenSecret,
                          @Value("${app.auth.tokenExpirationMsec}") int tokenExpiration) {
        this.tokenSecret = tokenSecret;
        this.tokenExpiration = tokenExpiration;
    }

    public String createToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("Started creating a new token for user: ");

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpiration);

        return Jwts.builder()
                .setSubject(userPrincipal.get_id())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String getUserIdFromToken(String token) {

        log.info("Started getting the user ID associated with token: " + token);

        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {

        log.info("Started validating authentication token: " + authToken);

        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            log.info("Successfully validated authentication token.");
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
