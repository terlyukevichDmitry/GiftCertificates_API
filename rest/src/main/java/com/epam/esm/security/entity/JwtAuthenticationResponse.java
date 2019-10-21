package com.epam.esm.security.entity;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public class JwtAuthenticationResponse {
    /**
     * accessToken.
     */
    private String accessToken;
    /**
     * tokenType.
     */
    private String tokenType;

    /**
     * Constructor with parameters.
     * @param accessToken object.
     * @param tokenType object.
     */
    public JwtAuthenticationResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    /**
     * Getter.
     * @return string.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Setter.
     * @param accessToken string.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Getter.
     * @return string.
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Setter.
     * @param tokenType string.
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}