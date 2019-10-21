package com.epam.esm.service.serviceimpl;

import com.epam.esm.abstractrow.AbstractRowAction;
import com.epam.esm.entity.Purchase;
import com.epam.esm.entity.User;
import com.epam.esm.mapperinterface.UserMapper;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class we use for doing crud and other operations in database with tag objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Service
public class UserServiceImpl extends AbstractRowAction implements UserService {
    /**
     * userMapper object to doing different operations in database using dao methods.
     */
    private UserMapper userMapper;
    /**
     * userGiftCertificateService object to doing different operations in database using dao methods.
     */
    private PurchaseService purchaseMapper;

    /**
     * Constructor to init data.
     * @param userMapper element.
     * @param purchaseMapper element.
     */
    public UserServiceImpl(UserMapper userMapper, PurchaseService purchaseMapper) {
        this.userMapper = userMapper;
        this.purchaseMapper = purchaseMapper;
    }

    /**
     * Read operation.
     * @param page for pagination.
     * @param limit for pagination.
     * @return list with user objects.
     */
    @Override
    public List<User> findAll(Integer page, Integer limit) {
        return userMapper.findAll(generateRowBounds(page, limit));
    }

    /**
     * Create operation.
     * @param user element to create in database.
     */
    @Override
    public void create(User user) {
        userMapper.create(user);
    }

    /**
     * Delete operation with transactional annotation.
     * @param id element for delete in database;
     */
    @Override
    public int delete(Integer id) {
        List<Purchase> purchases = purchaseMapper.findPurchaseByUserId(id, null, null);
        for (Purchase purchase: purchases) {
            purchaseMapper.delete(purchase);
        }
        return userMapper.delete(id);
    }

    /**
     * Update operation.
     * @param user element to update in database.
     */
    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    /**
     * Method to find user by login.
     * @param login to find in db.
     * @return list.
     */
    @Override
    public User findByLogin(String login) {
        return userMapper.findByLogin(login);
    }

    /**
     * Method to find user by id.
     * @param id to find in db.
     * @return list.
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
