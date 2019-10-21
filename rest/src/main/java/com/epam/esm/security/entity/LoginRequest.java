package com.epam.esm.security.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public class LoginRequest {
    /**
     * login.
     */
    @NotNull
    private String login;
    /**
     * login.
     */
    @NotNull
    private String password;

    /**
     * Getter.
     * @return login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter.
     * @param login string element.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter.
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter.
     * @param password string element.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}