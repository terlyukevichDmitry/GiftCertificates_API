package com.epam.esm.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Element.
     */
    private JwtTokenProvider tokenProvider;

    /**
     * Element.
     */
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor with parameters.
     * @param jwtTokenProvider object.
     * @param customUserDetailsService object.
     */
    JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                            CustomUserDetailsService customUserDetailsService) {
        this.tokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Method to check jwt token and get access if all good with token.
     * @param request object.
     * @param response object.
     * @param filterChain object.
     * @throws ServletException, because we'll have exception situation.
     * @throws IOException, because we'll have exception situation.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = getJwtFromRequest(request);
        UserDetails userDetails;
        if ((StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken))) {
            Integer userId = tokenProvider.getUserIdFromJWT(jwtToken);
            userDetails = customUserDetailsService.loadUserById(userId);
        } else {
            userDetails = customUserDetailsService.getAnonymous();
        }
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /**
     * @param request object.
     * @return string.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
