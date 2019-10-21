package com.epam.esm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Terliukevich Dzmitry
 */
public class Tag implements Entity {
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
     * Use for default.
     */
    public Tag() {
    }

    /**
     * Constructor with tag name.
     * @param name element.
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Constructor with parameters to init data for objects.
     * @param id element.
     * @param name element.
     */
    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor with tag id.
     * @param id element.
     */
    public Tag(Integer id) {
        this.id = id;
    }

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

    /**
     * Object method.
     * @return string value.
     */
    @Override
    public String toString() {
        return "Tag{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}

