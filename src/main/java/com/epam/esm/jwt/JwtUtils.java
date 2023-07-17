package com.epam.esm.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSigningKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("type", "access");

        return createToken(claims, userDetails,
                new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("type", "refresh");

        return createToken(claims, userDetails,
                new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)));
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails, Date expiration) {
        return Jwts.builder().setClaims(claims)
                .setHeaderParam(Header.TYPE, "JWT")
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    public Boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        final String type = extractClaim(token, claims -> claims.get("type", String.class));

        return (username.equals((userDetails.getUsername())) && !isTokenExpired(token) && type.equals("access"));
    }

    public Boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        final String type = extractClaim(token, claims -> claims.get("type", String.class));

        return (username.equals((userDetails.getUsername())) && !isTokenExpired(token) && type.equals("refresh"));
    }
}
