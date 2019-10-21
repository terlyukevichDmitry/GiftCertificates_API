package com.epam.esm.security;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Component
public class JwtTokenProvider {
    /**
     * Element.
     */
    private UserService userService;

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LogManager.getLogger(JwtTokenProvider.class);
    /**
     * Method to rear jwt secret of the file.
     */
    @Value("${jwt.token.secret}")
    private String jwtSecret;

    /**
     * Method to rear jwt secret of the file.
     */
    @Value("${jwt.token.expiration}")
    private int jwtExpirationInMs;

    /**
     * Constructor with parameters.
     * @param userService object.
     */
    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method to generate token.
     * @param authentication element.
     * @return string.
     */
    public String generateToken(Authentication authentication) {
        Date nowDate = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByLogin(userDetails.getUsername());

        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getLogin());
        claims.put("role", user.getRole());
        return Jwts.builder()
                .setId(Integer.toString(user.getId()))
                .setIssuedAt(nowDate)
                .setExpiration(expiryDate)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Method to get user id from JWT token.
     * @param jwtToken string.
     * @return int.
     */
    Integer getUserIdFromJWT(String jwtToken) {
        Map<String, Object> claims = (Map<String, Object>) Jwts.parser()
                .setSigningKey(jwtSecret).parse(jwtToken).getBody();
        Object idNumber = claims.get("id");
        return Integer.valueOf(String.valueOf(idNumber));
    }

    /**
     * Method to parse token and validate.
     * @param authToken string.
     * @return true or false.
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return false;
    }
}
