package com.epam.esm.service.serviceimpl;

import com.epam.esm.abstractrow.AbstractRowAction;
import com.epam.esm.entity.Purchase;
import com.epam.esm.mapperinterface.PurchaseMapper;
import com.epam.esm.service.PurchaseService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class we use for doing crud and other operations in database with tag objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Service
public class PurchaseServiceImpl extends AbstractRowAction implements PurchaseService {
    /**
     * userGiftCertificateMapper object to doing different operations in database using dao methods.
     */
    private PurchaseMapper purchaseMapper;

    /**
     * Constructor to init data.
     * @param purchaseMapper element.
     */
    public PurchaseServiceImpl(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    /**
     * Delete operation with transactional annotation.
     * @param purchase element for delete in database;
     */
    @Override
    public void delete(Purchase purchase) {
        purchaseMapper.delete(purchase);
    }

    /**
     * Read operation.
     * @return list with tagGiftCertificate objects.
     */
    @Override
    public List<Purchase> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Create operation.
     * @param purchase element to create in database.
     */
    @Override
    public void create(Purchase purchase) {
        purchaseMapper.create(purchase);
    }

    /**
     * Update operation. But we don't use with method so we throw exception, if we'll use this method.
     * @param purchase element to update in database.
     */
    @Override
    public void update(Purchase purchase) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method to find list by user id and giftCertificates id.
     * @param userId user id.
     * @param giftCertificateId giftCertificate id.
     * @return list with Purchase.
     */
    @Override
    public List<Purchase> findById(Integer userId, Integer giftCertificateId) {
        return purchaseMapper.findById(userId, giftCertificateId);
    }

    /**
     * Method to find list by user id.
     * @param userId user id.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list.
     */
    @Override
    public List<Purchase> findPurchaseByUserId(Integer userId,
                                               Integer page,
                                               Integer limit) {
        return purchaseMapper.findUserGiftCertificateByUserId(userId, generateRowBounds(page, limit));
    }
}
