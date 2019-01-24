package io.redspark.thot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TokenAuthenticationService {

    private static int EXPIRATION_TIME;
    private static String SECRET;
    private static String PREFIX;

    @Value("${thot.security.token.expiration}")
    public void setExpirationTime(int expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }

    @Value("${thot.security.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

    @Value("${thot.security.prefix}")
    public void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public static void addAuthentication(HttpServletResponse response, String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, String.format("%s %s", PREFIX, JWT));
    }

    public static Authentication getAuthentication(HttpServletRequest request, ThotUserDetailsService thotUserDetailsService) {
        String JWT = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (JWT != null) {
            String tokenWithoutPrefix = JWT.replace(PREFIX, "");

            String username = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(tokenWithoutPrefix)
                    .getBody()
                    .getSubject();

            UserDetails userDetails = thotUserDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());
            }
        }
        return null;
    }

}
