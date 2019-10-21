package com.epam.esm.security;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * This class we use for security all http queries.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * userService.
     */
    private UserService userService;

    /**
     * Constructor with parameter.
     * @param userService element.
     */
    public CustomUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    /**
     * @param username to check and load.
     * @return UserDetails.
     * @throws UsernameNotFoundException, because we'll have exception situation.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new LinkedHashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), grantedAuthorities);
    }

    /**
     * @param id to check and load.
     * @return UserDetails.
     * @throws UsernameNotFoundException, because we'll have exception situation.
     */
    @Transactional(readOnly = true)
    UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
        User user = userService.findById(id);
        if (user == null) throw new UsernameNotFoundException("" + id);

        Set<GrantedAuthority> grantedAuthorities = new LinkedHashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), grantedAuthorities);
    }

    /**
     * @return userDetails.
     * @throws UsernameNotFoundException, because we'll have exception situation.
     */
    @Transactional(readOnly = true)
    UserDetails getAnonymous() throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new LinkedHashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ANONYMOUS"));
        return new org.springframework.security.core.userdetails.User("ANONYMOUS",
                "ANONYMOUS", grantedAuthorities);
    }
}
