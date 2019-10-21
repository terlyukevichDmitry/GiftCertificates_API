package com.epam.esm.dto;

import com.epam.esm.entity.Entity;
import com.epam.esm.entity.Tag;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Terliukevich Dzmitry
 */
public class GiftCertificateDto implements Entity {
    /**
     * id.
     */
    private Integer id;
    /**
     * name.
     */
    @Size(min = 4, max = 64)
    private String name;
    /**
     * description.
     */
    @Size(max = 512)
    private String description;
    /**
     * price.
     */
    @Positive
    private Double price;
    /**
     * createDate.
     */
    private String createDate;
    /**
     * lastUpdateDate.
     */
    private String lastUpdateDate;
    /**
     * duration.
     */
    @Positive
    private Integer duration;

    private List<Tag> tags;

    @Valid
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Getter for id field.
     * @return integer value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter for name field.
     * @return string value.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for description field.
     * @return string value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for price field.
     * @return long value.
     */
    public Double getPrice() {
        return price;
    }
    /**
     * Getter for create date field.
     * @return string value.
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Getter for last update date field.
     * @return string value.
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Getter for duration field.
     * @return long value.
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Setter for id field.
     * @param id value to init element in object.
     */
    public void setId(Integer id) {
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
     * Setter for description field.
     * @param description value to init element in object.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Setter for price field.
     * @param price value to init element in object.
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    /**
     * Setter for createDate field.
     * @param createDate value ro init element in object.
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    /**
     * Setter for lastUpdateDate field.
     * @param lastUpdateDate value ro init element in object.
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    /**
     * Setter for duration field.
     * @param duration value ro init element in object.
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
