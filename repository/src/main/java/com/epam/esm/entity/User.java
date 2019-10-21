package com.epam.esm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Terliukevich Dzmitry
 */
public class User implements Entity {

    /**
     * User's identifier.
     */
    private Integer id;
    /**
     * User's Login.
     */
    @NotNull
    @Size(min = 4, max = 64)
    private String login;
    /**
     * User's password.
     */
    @NotNull
    @Size(min = 4, max = 100)
    private String password;
    /**
     * User's role.
     */
    @NotNull
    private String role;

    /**
     * Constructor without parameters.
     */
    public User() {
    }
    /**
     * Getter for id field.
     * @return integer value.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Setter for id field.
     * @param id value to init element in object.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for login field.
     * @return string value.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter for login field.
     * @param login value to init element in object.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter for password field.
     * @return string value.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password field.
     * @param password value to init element in object.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for role field.
     * @return string value.
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role field.
     * @param role value to init element in object.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Object method.
     * @return string value.
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password + '\''
                + ", role=" + role + '}';
    }
}
