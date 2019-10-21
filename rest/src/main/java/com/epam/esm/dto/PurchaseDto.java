package com.epam.esm.dto;

import com.epam.esm.entity.Entity;

/**
 * @author Terliukevich Dzmitry
 */
public class PurchaseDto implements Entity {
    /**
     * UserGift's purchaseDate.
     */
    private String purchaseDate;
    /**
     * UserGift's price.
     */
    private Integer userId;
    /**
     * UserGift's userId.
     */
    private Integer giftCertificateId;
    /**
     * UserGift's giftCertificateId.
     */
    private Double price;

    /**
     * Getter for purchase date field.
     * @return string value.
     */
    public String getPurchaseDate() {
        return purchaseDate;
    }
    /**
     * Setter for purchaseDate field.
     * @param purchaseDate value to init element in object.
     */
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    /**
     * Getter for user id field.
     * @return integer value.
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * Setter for userId field.
     * @param userId value to init element in object.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    /**
     * Getter for gift certificates id field.
     * @return integer value.
     */
    public Integer getGiftCertificateId() {
        return giftCertificateId;
    }
    /**
     * Setter for giftCertificateId field.
     * @param giftCertificateId value to init element in object.
     */
    public void setGiftCertificateId(Integer giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }
    /**
     * Getter for price field.
     * @return double value.
     */
    public Double getPrice() {
        return price;
    }
    /**
     * Setter for price field.
     * @param price value to init element in object.
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
