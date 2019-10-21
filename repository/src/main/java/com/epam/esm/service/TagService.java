package com.epam.esm.service;

import com.epam.esm.entity.Tag;

/**
 * Interface.
 */
public interface TagService extends CrudService<Tag> {
    /**
     * Method to find tags by tag name.
     * @param name element to find.
     * @return list with tags.
     */
    Tag findByName(String name);
    /**
     * Method to find tags by tag id.
     * @param id element to find.
     * @return list with tags.
     */
    Tag findById(Integer id);
    /**
     * Method to find the most popular tag.
     * @return tag in list.
     */
    Tag findMostPopularTag();
}
