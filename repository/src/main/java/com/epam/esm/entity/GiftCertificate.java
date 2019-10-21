package com.epam.esm.entity;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Terliukevich Dzmitry
 */
public class GiftCertificate implements Entity {
    /**
     * id.
     */
    private Integer id;
    /**
     * name.
     */
    @NotNull
    @Size(min = 4, max = 64)
    private String name;
    /**
     * description.
     */
    @NotNull
    @Size(max = 512)
    private String description;
    /**
     * price.
     */
    @NotNull
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
    @NotNull
    @Positive
    private Integer duration;
    /**
     * List with tags.
     */
    @Valid
    private List<Tag> tags;

    /**
     * Use for default.
     */
    public GiftCertificate() {
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

    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Setter for list with tag objects.
     * @param tags value to init element in object.
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * To check equal objects.
     * @param o object to equal.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(tags, that.tags);
    }

    /**
     * Hashcode.
     * @return int.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, createDate, lastUpdateDate, duration, tags);
    }

    /**
     * Object method.
     * @return string value.
     */
    @Override
    public String toString() {
        return "GiftCertificate{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\''
                + ", price=" + price + ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate
                + ", duration=" + duration + '}';
    }
}
