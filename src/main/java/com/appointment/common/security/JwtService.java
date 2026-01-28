package com.appointment.common.security;

import com.appointment.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j(topic = "JwtService")
public class JwtService {
    @Value("${security.jwt.access-token-secret-key}")
    private String accessTokenSecretKey;

    @Value("${security.jwt.access-token-expiration-time}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refresh-token-secret-key}")
    private String refreshTokenSecretKey;

    @Value("${security.jwt.refresh-token-expiration-time}")
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject, false);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getId, true);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean useRefreshTokenSecretKey) {
        String secretKey = accessTokenSecretKey;
        if (useRefreshTokenSecretKey) {
            secretKey = refreshTokenSecretKey;
        }
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    public Pair<String, String> generateTokens(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());
        return Pair.of(generateAccessToken(extraClaims, user), generateRefreshToken(extraClaims, user));
    }

    public String generateAccessToken(Map<String, Object> extraClaims, User user) {
        return buildToken(extraClaims, user, accessTokenSecretKey, accessTokenExpiration);
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, User user) {
        return buildToken(extraClaims, user, refreshTokenSecretKey, refreshTokenExpiration);
    }

    public long getAccessTokenExpirationTime() {
        return accessTokenExpiration;
    }

    public long getRefreshTokenExpirationTime() {
        return refreshTokenExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User user,
            String key,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(key), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration, false);
    }

    private Claims extractAllClaims(String token, String key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
