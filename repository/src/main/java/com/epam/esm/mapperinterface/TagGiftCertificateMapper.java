package com.epam.esm.mapperinterface;

import com.epam.esm.entity.TagGiftCertificate;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper interface with methods to work's with tagGiftCertificates table in database.
 * @author Terliukevich Dzmitry.
 * @since 1.0
 */
@Mapper
public interface TagGiftCertificateMapper {
    /**
     * Method to delete tagGiftCertificate.
     * @param tagGiftCertificate object to find element in database and delete element.
     */
    @Delete("DELETE from tag_giftcertificates WHERE tag_id = #{tagId} AND giftcertificates_id = #{giftCertificateId}")
    void delete(TagGiftCertificate tagGiftCertificate);

    /**
     * Method to create tagGiftCertificate.
     * @param tagGiftCertificate object to create element in database.
     */
    @Insert("INSERT into tag_giftcertificates(tag_id, giftcertificates_id) VALUES(#{tagId}, #{giftCertificateId})")
    void create(TagGiftCertificate tagGiftCertificate);

    /**
     * Method to find tagGiftCertificate by tag id.
     * @param tagId to find element in database.
     * @return list.
     */
    @Select("SELECT tag_id, giftcertificates_id FROM tag_giftcertificates where tag_id = #{tagId}")
    @Results(value = {
            @Result(property="tagId", column="tag_id"),
            @Result(property="giftCertificateId", column="giftcertificates_id"),
    })
    List<TagGiftCertificate> findByTagId(Integer tagId);

    /**
     * Method to find tagGiftCertificate by giftCertificate id.
     * @param giftCertificateId to find element in database.
     * @return list.
     */
    @Select("SELECT tag_id, giftcertificates_id FROM tag_giftcertificates where "
            + "giftcertificates_id = #{giftCertificateId}")
    @Results(value = {
            @Result(property="tagId", column="tag_id"),
            @Result(property="giftCertificateId", column="giftcertificates_id"),
    })
    List<TagGiftCertificate> findByGiftId(Integer giftCertificateId);
}
