package com.epam.esm.service.serviceimpl;

import com.epam.esm.abstractrow.AbstractRowAction;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Purchase;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagGiftCertificate;
import com.epam.esm.exception.DeleteConstraintException;
import com.epam.esm.mapperinterface.GiftCertificateMapper;
import com.epam.esm.mapperinterface.TagGiftCertificateMapper;
import com.epam.esm.mapperinterface.TagMapper;
import com.epam.esm.mapperinterface.PurchaseMapper;
import com.epam.esm.service.GiftCertificateService;

import org.apache.ibatis.session.RowBounds;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

/**
 * This class we use for doing crud and other operations in database with giftCertificate objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Service
public class GiftCertificateServiceImpl extends AbstractRowAction implements GiftCertificateService {

    /**
     * giftCertificateMapper object to doing different operations in database using dao methods.
     */
    private GiftCertificateMapper giftCertificateMapper;

    /**
     * tagMapper object to doing different operations in database using dao methods.
     */
    private TagMapper tagMapper;

    /**
     * tagGiftCertificateMapper object to doing different operations in database using dao methods.
     */
    private TagGiftCertificateMapper tagGiftCertificateMapper;

    /**
     * userGiftCertificateMapper object to doing different operations in database using dao methods.
     */
    private PurchaseMapper purchaseMapper;

    /**
     * Regular expression.
     */
    private final static String REGEX = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Constructor to init data.
     * @param giftCertificateMapper element.
     * @param tagMapper element.
     * @param tagGiftCertificateMapper element.
     * @param purchaseMapper element.
     */
    public GiftCertificateServiceImpl(GiftCertificateMapper giftCertificateMapper,
                                      TagMapper tagMapper,
                                      TagGiftCertificateMapper tagGiftCertificateMapper,
                                      PurchaseMapper purchaseMapper) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
        this.tagGiftCertificateMapper = tagGiftCertificateMapper;
        this.purchaseMapper = purchaseMapper;
    }

    /**
     * Delete operation.
     * @param id element for delete in database;
     */
    @Override
    public int delete(Integer id) throws DeleteConstraintException {
        List<Purchase> purchases = purchaseMapper.findByGiftCertificateId(id);
        if (purchases.size() >= 1) {
            StringBuilder errorMessage = new StringBuilder();
            for (Purchase purchase: purchases) {
                errorMessage.append(purchase.getUserId()).append("; ");
            }
            throw new DeleteConstraintException(errorMessage.toString());
        }
        for (TagGiftCertificate tagGiftCertificate :tagGiftCertificateMapper.findByGiftId(id)) {
            tagGiftCertificateMapper.delete(tagGiftCertificate);
        }
        return giftCertificateMapper.delete(id);
    }
    /**
     * Read operation.
     * @return list with giftCertificate objects.
     */
    @Override
    public List<GiftCertificate> findAll(Integer page,
                                         Integer limit) {
        throw new UnsupportedOperationException();
    }

    /**
     * Create operation.
     * @param giftCertificate element to create in database.
     */
    @Override
    public void create(GiftCertificate giftCertificate) {
        giftCertificate.setCreateDate(LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern(REGEX)));
        giftCertificate.setLastUpdateDate(LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern(REGEX)));
        giftCertificateMapper.create(giftCertificate);
        createTag(giftCertificate.getTags(), giftCertificate);
    }
    /**
     * Update operation.
     * @param giftCertificate element to update in database.
     */
    @Override
    public void update(GiftCertificate giftCertificate) {
        GiftCertificate expected = updateGiftCertificateElement(giftCertificate,
                giftCertificateMapper.findByGiftCertificateId(giftCertificate.getId()));
        for (TagGiftCertificate tagGiftCertificate :tagGiftCertificateMapper.findByGiftId(giftCertificate.getId())) {
            tagGiftCertificateMapper.delete(tagGiftCertificate);
        }
        giftCertificateMapper.update(expected);
        createTag(giftCertificate.getTags(), expected);
    }

    /**
     * Method to find elements by name.
     * @param name element.
     * @return list with giftCertificates.
     */
    @Override
    public List<GiftCertificate> findByName(String name) {
        return giftCertificateMapper.findByName(name);
    }

    /**
     * Method to find elements by id.
     * @param id element.
     * @return list with giftCertificates.
     */
    @Override
    public GiftCertificate findByCertificateId(Integer id) {
        GiftCertificate giftCertificate = giftCertificateMapper.findByGiftCertificateId(id);
        if (!checkEntity(giftCertificate)) {
            initializeGiftCertificate(id, giftCertificate);
        }
        return giftCertificate;
    }

    /**
     * Method to create tags in db.
     * @param tags to create.
     * @param giftCertificate object.
     */
    private void createTag(List<Tag> tags, GiftCertificate giftCertificate) {
        for (Tag newTag : tags) {
            boolean checker = false;
            for (Tag tag :tagMapper.findAll(new RowBounds())) {
                if (newTag.getName().equals(tag.getName())) {
                    checker = true;
                    TagGiftCertificate tagGiftCertificate = new TagGiftCertificate();
                    tagGiftCertificate.setTagId(tagMapper.findByName(newTag.getName()).getId());
                    GiftCertificate giftCertificates = findByCertificateId(giftCertificate.getId());
                    tagGiftCertificate.setGiftCertificateId(giftCertificates.getId());
                    tagGiftCertificateMapper.create(tagGiftCertificate);
                }
            }
            if (!checker) {
                tagMapper.create(newTag);
                TagGiftCertificate tagGiftCertificate = new TagGiftCertificate();
                tagGiftCertificate.setTagId(tagMapper.findByName(newTag.getName()).getId());
                GiftCertificate giftCertificates = findByCertificateId(giftCertificate.getId());
                tagGiftCertificate.setGiftCertificateId(giftCertificates.getId());
                tagGiftCertificateMapper.create(tagGiftCertificate);
            }
        }
    }

    /**
     * Method to update only not null elements.
     * @param giftCertificate object.
     * @param expected object.
     * @return new giftCertificate.
     */
    private GiftCertificate updateGiftCertificateElement(GiftCertificate giftCertificate,
                                                         GiftCertificate expected) {
        expected.setLastUpdateDate(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(REGEX)));

        if (giftCertificate.getDuration() != null) {
            expected.setDuration(giftCertificate.getDuration());
        }

        if (giftCertificate.getName() != null) {
            expected.setName(giftCertificate.getName());
        }

        if (giftCertificate.getPrice() != null) {
            expected.setPrice(giftCertificate.getPrice());
        }

        if (giftCertificate.getDescription() != null) {
            expected.setDescription(giftCertificate.getDescription());
        }
         return expected;
    }

    /**
     * Method to update price for giftCertificates.
     * @param giftCertificate object.
     */
    @Override
    public void updatePrice(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern(REGEX)));
        giftCertificateMapper.updatePrice(giftCertificate);
    }

    /**
     * Method to sort elements in db.
     * @param queryElements to sort by parameters.
     * @return list.
     */
    @Override
    public List<GiftCertificate> findSortQuery(Map<String, Object> queryElements) {
        List<GiftCertificate> giftCertificates = new ArrayList<>(
                new LinkedHashSet<>(giftCertificateMapper.findSortQuery(queryElements,
                        generateRowBounds((Integer) queryElements.get("page"), (Integer) queryElements.get("limit")))));
        for (GiftCertificate giftCertificate :giftCertificates) {
            initializeGiftCertificate(giftCertificate.getId(), giftCertificate);
        }
        return giftCertificates;
    }

    /**
     * Method to init giftCertificates.
     * @param id number to find in db;
     * @param giftCertificate object.
     */
    private void initializeGiftCertificate(Integer id, GiftCertificate giftCertificate) {
        List<Tag> tags = new ArrayList<>();
        for (TagGiftCertificate tagGiftCertificate :tagGiftCertificateMapper.findByGiftId(id)) {
            tags.add(tagMapper.findById(tagGiftCertificate.getTagId()));
        }
        giftCertificate.setTags(tags);
    }
}
