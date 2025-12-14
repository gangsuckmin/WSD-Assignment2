package kr.ac.jbnu.ksm.assignment2.auth.service;

import kr.ac.jbnu.ksm.assignment2.security.JwtTokenProvider;
import kr.ac.jbnu.ksm.assignment2.token.entity.Token;
import kr.ac.jbnu.ksm.assignment2.token.entity.TokenType;
import kr.ac.jbnu.ksm.assignment2.token.repository.TokenRepository;
import kr.ac.jbnu.ksm.assignment2.user.entity.User;
import kr.ac.jbnu.ksm.assignment2.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthService
{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final long refreshExpDays;

    public AuthService(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            @Value("${jwt.refresh-exp-days}") long refreshExpDays
    )
    {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshExpDays = refreshExpDays;
    }

    public record LoginResult(String accessToken, String refreshToken) {}
    public record RefreshResult(String accessToken) {}

    public LoginResult login(String loginId, String password)
    {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!user.isActive()) throw new RuntimeException("USER_DEACTIVATED");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("INVALID_CREDENTIALS");

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole().toSecurityRole());

        String refreshRaw = UUID.randomUUID() + "-" + UUID.randomUUID();
        String refreshHash = sha256(refreshRaw);

        LocalDateTime expiresAt = LocalDateTime.now().plusDays(refreshExpDays);
        tokenRepository.save(new Token(user.getId(), TokenType.refresh, refreshHash, expiresAt));

        return new LoginResult(accessToken, refreshRaw);
    }

    public RefreshResult refresh(String refreshRaw)
    {
        String refreshHash = sha256(refreshRaw);
        Token token = tokenRepository.findByTokenHashAndTypeAndRevokedAtIsNull(refreshHash, TokenType.refresh)
                .orElseThrow(() -> new RuntimeException("UNAUTHORIZED"));

        if (token.isExpired(LocalDateTime.now())) throw new RuntimeException("TOKEN_EXPIRED");

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        if (!user.isActive()) throw new RuntimeException("USER_DEACTIVATED");

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole().toSecurityRole());
        return new RefreshResult(accessToken);
    }

    private static String sha256(String raw)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes());
            return HexFormat.of().formatHex(digest);
        }
        catch (Exception e)
        {
            throw new RuntimeException("INTERNAL_SERVER_ERROR");
        }
    }
}