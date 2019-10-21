package com.epam.esm.dto;

import com.epam.esm.entity.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Terliukevich Dzmitry
 */
public class UserDto implements Entity {

    /**
     * User's identifier.
     */
    private Integer id;
    /**
     * User's role.
     */
    private String role;

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
}
