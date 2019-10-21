package com.epam.esm.mapperinterface;

import com.epam.esm.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Mapper interface with methods to work's with tag table in database.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Mapper
public interface TagMapper {
    /**
     * Method to find all element in database.
     * @param rowBounds pagination.
     * @return list.
     */
    @Select("SELECT id ,name FROM tag")
    List<Tag> findAll(RowBounds rowBounds);

    /**
     * Method to delete tag by id.
     * @param tagId to find element in database and delete element.
     */
    @Delete("DELETE FROM tag WHERE id = #{tagId}")
    int delete(Integer tagId);

    /**
     * Method to create giftCertificates by id.
     * @param tag create element.
     */
    @Insert("INSERT INTO tag(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(Tag tag);
    /**
     * Method to find tag by name.
     * @param tagName to find element in database.
     * @return list.
     */
    @Select("SELECT id, name FROM tag WHERE name=#{tagName}")
    Tag findByName(String tagName);

    /**
     * Method to find tag by id.
     * @param tagId to find element in database.
     * @return list.
     */
    @Select("SELECT id, name FROM tag WHERE id=#{tagId}")
    Tag findById(Integer tagId);

    /**
     * Method to find the most popular tag.
     * @return list.
     */
    @Select("select tag.name from tag inner join tag_giftcertificates on tag_giftcertificates.tag_id = tag.id " +
            "inner join giftcertificates on tag_giftcertificates.giftcertificates_id = giftcertificates.id " +
            "left join user_giftcertificates on user_giftcertificates.user_id = (select user.id from user " +
            "inner join user_giftcertificates on user_giftcertificates.user_id = user.id limit 1) " +
            "group by tag.name order by count(tag.name) desc limit 1;")
    Tag findMostPopularTag();
}
