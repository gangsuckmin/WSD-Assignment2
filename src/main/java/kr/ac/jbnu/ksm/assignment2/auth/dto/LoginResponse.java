package kr.ac.jbnu.ksm.assignment2.auth.dto;

public class LoginResponse
{
    public String tokenType = "Bearer";
    public String accessToken;
    public String refreshToken;

    public LoginResponse(String accessToken, String refreshToken)
    {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}