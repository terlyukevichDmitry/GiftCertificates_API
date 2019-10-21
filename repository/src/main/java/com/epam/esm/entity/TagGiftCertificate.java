package com.epam.esm.entity;

import java.util.Objects;

/**
 * @author Terliukevich Dzmitry
 */
public class TagGiftCertificate implements Entity {

    /**
     * Tag id.
     */
    private Integer tagId;
    /**
     * GiftCertificate id.
     */
    private Integer giftCertificateId;

    /**
     * Use for default.
     */
    public TagGiftCertificate() {}

    /**
     * Constructor with parameters to init data for object.
     * @param tagId integer value.
     * @param giftCertificateId integer value.
     */
    public TagGiftCertificate(Integer tagId, Integer giftCertificateId) {
        this.tagId = tagId;
        this.giftCertificateId = giftCertificateId;
    }

    /**
     * Getter for tag id.
     * @return integer value.
     */
    public Integer getTagId() {
        return tagId;
    }

    /**
     * Setter for id field.
     * @param tagId value to init element in object.
     */
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    /**
     * Getter for gift certificates id.
     * @return integer value.
     */
    public Integer getGiftCertificateId() {
        return giftCertificateId;
    }

    /**
     * Setter for id field.
     * @param giftCertificateId value to init element in object.
     */
    public void setGiftCertificateId(Integer giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    /**
     * To check equal objects.
     * @param o object to equal.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TagGiftCertificate that = (TagGiftCertificate) o;
        return Objects.equals(tagId, that.tagId) &&
                Objects.equals(giftCertificateId, that.giftCertificateId);
    }

    /**
     * Hashcode.
     * @return int.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tagId, giftCertificateId);
    }

    /**
     * Object method.
     * @return string value.
     */
    @Override
    public String toString() {
        return "TagGiftCertificate{" + "tagId=" + tagId + ", giftCertificateId=" + giftCertificateId + '}';
    }
}
