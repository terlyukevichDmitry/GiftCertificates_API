package com.epam.esm.service;

import com.epam.esm.entity.TagGiftCertificate;

import java.util.List;

/**
 * Interface.
 */
public interface TagGiftCertificateService {

    /**
     * Delete operation.
     * @param tagGiftCertificate element for delete in database;
     */
    void delete(TagGiftCertificate tagGiftCertificate);
    /**
     * Read operation.
     * @return list with <T> objects.
     */
    List<TagGiftCertificate> findAll();
    /**
     * Create operation.
     * @param tagGiftCertificate element to create in database.
     */
    void create(TagGiftCertificate tagGiftCertificate);
    /**
     * Update operation.
     * @param tagGiftCertificate element to update in database.
     */
    void update(TagGiftCertificate tagGiftCertificate);
}
