package kr.ac.jbnu.ksm.assignment2.auth.dto;

public class TokenResponse
{
    public String tokenType = "Bearer";
    public String accessToken;

    public TokenResponse(String accessToken)
    {
        this.accessToken = accessToken;
    }
}