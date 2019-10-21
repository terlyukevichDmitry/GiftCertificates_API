package com.epam.esm.dto;

import com.epam.esm.entity.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Terliukevich Dzmitry
 */
public class TagDto implements Entity {
    /**
     * id.
     */
    private Integer id;
    /**
     * name.
     */
    @NotNull
    @Size(min = 4, max = 32)
    private String name;

    /**
     * Setter for name field.
     * @param name value to init element in object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for id field.
     * @param id value to init element in object.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for name field.
     * @return string value.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for id field.
     * @return integer value.
     */
    public Integer getId() {
        return id;
    }

}