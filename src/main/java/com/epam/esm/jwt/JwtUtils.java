package com.epam.esm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * JwtUtils is a component responsible for handling JSON Web Tokens (JWTs) in a Spring Security-enabled
 * application. It provides methods for generating, parsing, and validating JWTs for both access and
 * refresh tokens.
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSigningKey;

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from a JWT token using a custom claims resolver.
     *
     * @param token          The JWT token from which to extract the claim.
     * @param claimsResolver The function used to resolve the claim from the token's claims.
     * @param <T>            The type of the claim.
     * @return The claim extracted from the token.
     */
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

    /**
     * Generates an access token for a user.
     *
     * @param userDetails The UserDetails representing the user.
     * @return The generated access token.
     */
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("type", "access");

        return createToken(claims, userDetails,
                new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
    }

    /**
     * Generates a refresh token for a user.
     *
     * @param userDetails The UserDetails representing the user.
     * @return The generated refresh token.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("type", "refresh");

        return createToken(claims, userDetails,
                new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)));
    }

    /**
     * Creates a JWT token based on the provided claims, user details, and expiration date.
     *
     * @param claims      The claims to include in the token.
     * @param userDetails The UserDetails representing the user.
     * @param expiration  The expiration date for the token.
     * @return The generated JWT token.
     */
    private String createToken(Map<String, Object> claims, UserDetails userDetails, Date expiration) {
        return Jwts.builder().setClaims(claims)
                .setHeaderParam(Header.TYPE, "JWT")
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    /**
     * Checks if an access token is valid for a specific user.
     *
     * @param token       The access token to validate.
     * @param userDetails The UserDetails representing the user.
     * @return True if the access token is valid, false otherwise.
     */
    public Boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        final String type = extractClaim(token, claims -> claims.get("type", String.class));

        return (username.equals((userDetails.getUsername())) && !isTokenExpired(token) && type.equals("access"));
    }

    /**
     * Checks if a refresh token is valid for a specific user.
     *
     * @param token       The refresh token to validate.
     * @param userDetails The UserDetails representing the user.
     * @return True if the refresh token is valid, false otherwise.
     */
    public Boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        final String type = extractClaim(token, claims -> claims.get("type", String.class));

        return (username.equals((userDetails.getUsername())) && !isTokenExpired(token) && type.equals("refresh"));
    }
}
