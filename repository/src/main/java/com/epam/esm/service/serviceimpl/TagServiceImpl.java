package com.epam.esm.service.serviceimpl;

import com.epam.esm.abstractrow.AbstractRowAction;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGiftCertificate;
import com.epam.esm.mapperinterface.TagGiftCertificateMapper;
import com.epam.esm.mapperinterface.TagMapper;
import com.epam.esm.service.TagService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class we use for doing crud and other operations in database with tag objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Service
public class TagServiceImpl extends AbstractRowAction implements TagService {
    /**
     * tagMapper object to doing different operations in database using dao methods.
     */
    private TagMapper tagMapper;

    /**
     * TagGiftCertificateDao object to doing different operations in database using dao methods.
     */
    private TagGiftCertificateMapper tagGiftCertificateMapper;

    /**
     * Constructor to init data.
     * @param tagMapper element.
     * @param tagGiftCertificateMapper element.
     */
    public TagServiceImpl(TagMapper tagMapper,
                          TagGiftCertificateMapper tagGiftCertificateMapper) {
        this.tagMapper = tagMapper;
        this.tagGiftCertificateMapper = tagGiftCertificateMapper;
    }

    /**
     * Delete operation.
     * @param id element for delete in database;
     */
    @Override
    public int delete(Integer id) {
        for (TagGiftCertificate tagGiftCertificate: tagGiftCertificateMapper.findByTagId(id)) {
            tagGiftCertificateMapper.delete(tagGiftCertificate);
        }
        return tagMapper.delete(id);
    }

    /**
     * Read operation.
     * @return list with tag objects.
     */
    @Override
    public List<Tag> findAll(Integer page, Integer limit) {
        return tagMapper.findAll(generateRowBounds(page, limit));
    }

    /**
     * Create operation.
     * @param tag element to create in database.
     */
    @Override
    public void create(Tag tag) {
        tagMapper.create(tag);
    }

    /**
     * Update operation.
     * @param tag element to update in database.
     */
    @Override
    public void update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method to find elements by name.
     * @param name element.
     * @return tag.
     */
    @Override
    public Tag findByName(String name) {
        return tagMapper.findByName(name);
    }

    /**
     * Method to find elements by name.
     * @param id element.
     * @return list with tags.
     */
    @Override
    public Tag findById(Integer id) {
        return tagMapper.findById(id);
    }

    /**
     * Method to find the most popular tag.
     * @return tag in list.
     */
    @Override
    public Tag findMostPopularTag() {
        return tagMapper.findMostPopularTag();
    }
}
