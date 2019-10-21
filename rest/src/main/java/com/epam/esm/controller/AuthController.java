package com.epam.esm.controller;

import com.epam.esm.action.AbstractAction;
import com.epam.esm.aspect.aspectsannotation.Requested;
import com.epam.esm.exception.FindObjectException;
import com.epam.esm.security.entity.JwtAuthenticationResponse;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.security.entity.LoginRequest;

import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * It's controller fot authenticate users.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@RestController
@Transactional
@RequestMapping
public class AuthController extends AbstractAction {
    /**
     * authenticationManager.
     */
    private AuthenticationManager authenticationManager;
    /**
     * tokenProvider.
     */
    private JwtTokenProvider tokenProvider;
    /**
     * userService.
     */
    private UserService userService;
    /**
     * token type.
     */
    private static final String tokenType = "bearer";

    /**
     * Constructor with parameters for init data.
     * @param authenticationManager object.
     * @param tokenProvider object.
     * @param userService object.
     */
    public AuthController(final AuthenticationManager authenticationManager,
                          final JwtTokenProvider tokenProvider,
                          final UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * Method to authenticate users.
     * @param loginRequest object with login and password.
     * @return responseEntity.
     */
    @Requested
    @PostMapping(value = "/api/auth/signin", produces = "application/json")
    @PreAuthorize("hasAuthority('ANONYMOUS')")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                                      HttpServletResponse httpServletResponse)
            throws FindObjectException {
        checkEntityExistence(userService.findByLogin(loginRequest.getLogin()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        String token = tokenProvider.generateToken(authentication);
        httpServletResponse.setHeader("token", token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, tokenType));
    }
}
