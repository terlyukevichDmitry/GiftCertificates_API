package com.epam.esm.service;

import com.epam.esm.exception.DeleteConstraintException;

import java.util.List;

/**
 * Interface for crud operations.
 * @param <T> entity.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public interface CrudService<T> {
    /**
     * Delete operation.
     * @param id element for delete in database;
     */
    int delete(Integer id) throws DeleteConstraintException;
    /**
     * Read operation.
     * @return list with <T> objects.
     */
    List<T> findAll(Integer page, Integer limit);
    /**
     * Create operation.
     * @param entity element to create in database.
     */
    void create(T entity);
    /**
     * Update operation.
     * @param entity element to update in database.
     */
    void update(T entity);
}
