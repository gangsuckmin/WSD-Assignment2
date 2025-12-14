package kr.ac.jbnu.ksm.assignment2.auth.web;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksm.assignment2.auth.dto.LoginRequest;
import kr.ac.jbnu.ksm.assignment2.auth.dto.LoginResponse;
import kr.ac.jbnu.ksm.assignment2.auth.dto.RefreshRequest;
import kr.ac.jbnu.ksm.assignment2.auth.dto.TokenResponse;
import kr.ac.jbnu.ksm.assignment2.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController
{

    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req)
    {
        var r = authService.login(req.loginId(), req.password());
        return new LoginResponse(r.accessToken(), r.refreshToken());
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshRequest req)
    {
        var r = authService.refresh(req.refreshToken());
        return new TokenResponse(r.accessToken());
    }
}