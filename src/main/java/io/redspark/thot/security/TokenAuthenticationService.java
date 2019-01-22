package io.redspark.thot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.redspark.thot.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

public class TokenAuthenticationService {

    // TODO extract properties
    private static final int EXPIRATION_TIME = 300_000;
    private static final String SECRET = "thotsecret";
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer";

    public static void addAuthentication(HttpServletResponse response, String username){
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.addHeader(HEADER, String.format("%s %s", PREFIX, JWT));
    }

    public static Authentication getAuthentication(HttpServletRequest request, ThotUserDetailsService thotUserDetailsService){
        String JWT = request.getHeader(HEADER);

        if(JWT != null){
            String tokenWithoutPrefix = JWT.replace(PREFIX, "");

            String username = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(tokenWithoutPrefix)
                    .getBody()
                    .getSubject();

            UserDetails userDetails = thotUserDetailsService.loadUserByUsername(username);

            if(userDetails != null){
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());
            }
        }
        return null;
    }

}
