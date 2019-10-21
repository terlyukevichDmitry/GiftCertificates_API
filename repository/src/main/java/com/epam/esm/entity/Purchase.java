package com.epam.esm.entity;

/**
 * @author Terliukevich Dzmitry
 */
public class Purchase implements Entity {

    /**
     * UserGift's purchaseDate.
     */
    private String purchaseDate;
    /**
     * UserGift's price.
     */
    private Double price;
    /**
     * UserGift's userId.
     */
    private Integer userId;
    /**
     * UserGift's giftCertificateId.
     */
    private Integer giftCertificateId;

    /**
     * Constructor without parameters.
     */
    public Purchase() {
    }
    /**
     * Constructor with parameters.
     */
    public Purchase(final String purchaseDate, final Double price, final Integer userId,
                    final Integer giftCertificateId) {
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.userId = userId;
        this.giftCertificateId = giftCertificateId;
    }

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

    /**
     * Object method.
     * @return string value.
     */
    @Override
    public String toString() {
        return "Purchase{" + "purchaseDate='" + purchaseDate + '\'' + ", price=" + price + ", userId="
                + userId + ", giftCertificateId=" + giftCertificateId + '}';
    }
}
