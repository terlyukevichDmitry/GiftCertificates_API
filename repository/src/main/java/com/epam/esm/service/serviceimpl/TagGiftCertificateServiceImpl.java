package com.epam.esm.service.serviceimpl;

import com.epam.esm.entity.TagGiftCertificate;
import com.epam.esm.mapperinterface.TagGiftCertificateMapper;
import com.epam.esm.service.TagGiftCertificateService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class we use for doing crd and other operations in database with tag giftCertificates objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Service
public class TagGiftCertificateServiceImpl implements TagGiftCertificateService {

    /**
     * tagGiftCertificateMapper object to doing different operations in database using dao methods.
     */
    private TagGiftCertificateMapper tagGiftCertificateMapper;

    /**
     * Constructor to init data.
     * @param tagGiftCertificateMapper element.
     */
    public TagGiftCertificateServiceImpl(TagGiftCertificateMapper tagGiftCertificateMapper) {
        this.tagGiftCertificateMapper = tagGiftCertificateMapper;
    }

    /**
     * Delete operation.
     * @param tagGiftCertificate element for delete in database;
     */
    @Override
    public void delete(TagGiftCertificate tagGiftCertificate) {
        tagGiftCertificateMapper.delete(tagGiftCertificate);
    }

    /**
     * Read operation.
     * @return list with tagGiftCertificate objects.
     */
    @Override
    public List<TagGiftCertificate> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create operation.
     * @param tagGiftCertificate element to create in database.
     */
    @Override
    public void create(TagGiftCertificate tagGiftCertificate) {
        tagGiftCertificateMapper.create(tagGiftCertificate);
    }

    /**
     * Update operation. But we don't use with method so we throw exception, if we'll use this method.
     * @param tagGiftCertificate element to update in database.
     */
    @Override
    public void update(TagGiftCertificate tagGiftCertificate) {
        throw new UnsupportedOperationException();
    }
}
