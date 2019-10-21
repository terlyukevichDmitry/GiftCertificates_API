package com.epam.esm.service;

import com.epam.esm.entity.User;

/**
 * This interface we use for doing crud and other operations in database with tag objects.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public interface UserService extends CrudService<User> {
    /**
     * Method to find user by login.
     * @param login to find in db.
     * @return list.
     */
    User findByLogin(String login);
    /**
     * Method to find user by id.
     * @param id to find in db.
     * @return list.
     */
    User findById(Integer id);
}
