package kr.ac.jbnu.ksm.assignment2.token.repository;

import kr.ac.jbnu.ksm.assignment2.token.entity.Token;
import kr.ac.jbnu.ksm.assignment2.token.entity.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer>
{
    Optional<Token> findByTokenHashAndTypeAndRevokedAtIsNull(String tokenHash, TokenType type);
}