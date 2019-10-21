package com.epam.esm.service;

import com.epam.esm.entity.Purchase;

import java.util.List;

/**
 * This class we use for doing crud and other operations in database with tag objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public interface PurchaseService {

    /**
     * Delete operation.
     * @param purchase element for delete in database;
     */
    void delete(Purchase purchase);
    /**
     * Read operation.
     * @return list with <T> objects.
     */
    List<Purchase> findAll();
    /**
     * Create operation.
     * @param purchase element to create in database.
     */
    void create(Purchase purchase);
    /**
     * Update operation.
     * @param purchase element to update in database.
     */
    void update(Purchase purchase);

    /**
     * Method to find list by user id and giftCertificates id.
     * @param userId user id.
     * @param giftCertificateId giftCertificate id.
     * @return list with Purchase.
     */
    List<Purchase> findById(Integer userId, Integer giftCertificateId);

    /**
     * Method to find list by user id.
     * @param userId user id.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list.
     */
    List<Purchase> findPurchaseByUserId(Integer userId, Integer page, Integer limit);
}
