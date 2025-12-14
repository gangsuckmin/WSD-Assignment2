package kr.ac.jbnu.ksm.assignment2.token.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class Token
{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 16)
    private TokenType type;

    @Column(name = "token_hash", nullable = false, length = 128)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    protected Token() {}

    public Token(Integer userId, TokenType type, String tokenHash, LocalDateTime expiresAt)
    {
        this.userId = userId;
        this.type = type;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }

    public Integer getUserId() { return userId; }
    public TokenType getType() { return type; }
    public String getTokenHash() { return tokenHash; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public LocalDateTime getRevokedAt() { return revokedAt; }

    public boolean isRevoked() { return revokedAt != null; }
    public boolean isExpired(LocalDateTime now) { return expiresAt.isBefore(now) || expiresAt.isEqual(now); }

    public void revoke(LocalDateTime now) { this.revokedAt = now; }
}
