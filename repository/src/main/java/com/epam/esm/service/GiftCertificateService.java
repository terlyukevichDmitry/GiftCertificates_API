package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

/**
 * Interface with methods for giftCertificate objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public interface GiftCertificateService extends CrudService<GiftCertificate> {
    /**
     * Method to find gift certificate by name.
     * @return list with giftCertificates.
     */
    List<GiftCertificate> findByName(String name);
    /**
     * Method to find gift certificate by name.
     * @return list with giftCertificates.
     */
    GiftCertificate findByCertificateId(Integer id);
    /**
     * Method to sort elements in db.
     * @param map to sort by parameters.
     * @return list.
     */
    List<GiftCertificate> findSortQuery(Map<String, Object> map);
    /**
     * Method to update price for giftCertificates.
     * @param giftCertificate object.
     */
    void updatePrice(GiftCertificate giftCertificate);
}
