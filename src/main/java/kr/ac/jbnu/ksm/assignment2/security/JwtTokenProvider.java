package kr.ac.jbnu.ksm.assignment2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.ac.jbnu.ksm.assignment2.user.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider
{

    private final SecretKey key;
    private final long accessExpMin;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-exp-min}") long accessExpMin
    )
    {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpMin = accessExpMin;
    }

    public String createAccessToken(Integer userId, Role role)
    {
        Instant now = Instant.now();
        Instant exp = now.plus(accessExpMin, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("role", role.name()) // ROLE_USER / ROLE_ADMIN
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public boolean validate(String token)
    {
        try
        {
            parseClaims(token);
            return true;
        }
        catch (Exception e)
        {
            return false; // 만료/위조 등 전부 false
        }
    }

    public Claims parseClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(String token)
    {
        Claims claims = parseClaims(token);
        String userId = claims.getSubject();
        String role = (String) claims.get("role"); // ROLE_USER / ROLE_ADMIN

        var auth = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                List.of(new SimpleGrantedAuthority(role))
        );
        return auth;
    }
}