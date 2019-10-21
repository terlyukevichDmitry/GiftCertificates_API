package com.epam.esm.mapperinterface;

import com.epam.esm.entity.GiftCertificate;

import com.epam.esm.entity.Tag;
import com.epam.esm.sqlgenerator.SqlGenerator;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Mapper interface with methods to work's with giftCertificates table in database.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@Mapper
public interface GiftCertificateMapper {
    /**
     * Method to find giftCertificates by name.
     * @param name to find element in database.
     * @return list.
     */
    @Select("SELECT id, name, description, price, createDate, lastUpdateDate, duration "
            + "FROM giftcertificates WHERE name=#{name}")
    List<GiftCertificate> findByName(String name);

    /**
     * Method to find giftCertificates by giftCertificates id.
     * @param giftCertificateId to find element in database.
     * @return list.
     */
    @Select("SELECT id, name, description, price, createDate, lastUpdateDate, duration"
            + " FROM giftcertificates WHERE id=#{giftCertificateId}")
    GiftCertificate findByGiftCertificateId(@Param("giftCertificateId") Integer giftCertificateId);

    /**
     * Method to delete giftCertificates by id.
     * @param giftCertificateId to find element in database and delete element.
     */
    @Delete("DELETE FROM giftcertificates WHERE id=#{giftCertificateId}")
    int delete(Integer giftCertificateId);

    /**
     * Method to create giftCertificates.
     * @param giftCertificate object to create element in database.
     */
    @Insert("INSERT INTO giftcertificates(name, description, price, createDate, lastUpdateDate, duration) "
            + "VALUES(#{name}, #{description}, #{price}, #{createDate}, #{lastUpdateDate}, #{duration})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(GiftCertificate giftCertificate);

    /**
     * Method to update giftCertificates.
     * @param giftCertificate to update element in database.
     */
    @Update("UPDATE giftcertificates SET name=#{name}, description=#{description}, price=#{price}, "
            + "createDate=#{createDate}, lastUpdateDate=#{lastUpdateDate}, duration=#{duration} WHERE id=#{id}")
    void update(GiftCertificate giftCertificate);

    /**
     * Method to update giftCertificates.
     * @param map elements.
     * @param rowBounds pagination.
     * @return list.
     */
    @SelectProvider(type= SqlGenerator.class, method="generateSortQuery")
    List<GiftCertificate> findSortQuery(Map<String, Object> map, RowBounds rowBounds);
    /**
     * Method to update giftCertificates by price.
     * @param giftCertificate to update element in database.
     */
    @Update("UPDATE giftcertificates SET price=#{price}, lastUpdateDate=#{lastUpdateDate} WHERE id=#{id}")
    void updatePrice(GiftCertificate giftCertificate);
}