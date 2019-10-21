package com.epam.esm.mapperinterface;

import com.epam.esm.entity.Purchase;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Mapper interface with methods to work's with userGiftCertificate table in database.
 * @author Terliukevich Dzmitry.
 * @since 1.0
 */
@Mapper
public interface PurchaseMapper {
    /**
     * Method to create userGiftCertificate.
     * @param userGiftCertificate object to create element in database.
     */
    @Insert("INSERT into user_giftcertificates(purchaseDate, price, user_id, giftcertificates_id) "
            + "VALUES(#{purchaseDate}, #{price}, #{userId}, #{giftCertificateId})")
    void create(Purchase userGiftCertificate);

    /**
     * Method to find Purchase by userId and giftCertificateId.
     * @param userId to find element in database.
     * @param giftCertificateId to find element in database.
     * @return list.
     */
    @Select("SELECT purchaseDate, price, user_id, giftcertificates_id FROM user_giftcertificates "
            + "WHERE user_id = #{userId} AND giftcertificates_id = #{giftCertificateId}")
    @Results(value = {
            @Result(property="purchaseDate", column = "purchaseDate"),
            @Result(property="userId", column="user_id"),
            @Result(property="giftCertificateId", column = "giftcertificates_id"),
    })
    List<Purchase> findById(@Param("userId") Integer userId,
                            @Param("giftCertificateId") Integer giftCertificateId);

    /**
     * Method to find Purchase by userId.
     * @param userId to find element in database.
     * @param rowBounds pagination.
     * @return list.
     */
    @Select("SELECT purchaseDate, price, user_id, giftcertificates_id FROM "
            + "user_giftcertificates WHERE user_id = #{userId}")
    @Results(value = {
            @Result(property="purchaseDate", column = "purchaseDate"),
            @Result(property="userId", column="user_id"),
            @Result(property="giftCertificateId", column = "giftcertificates_id"),
    })
    List<Purchase> findUserGiftCertificateByUserId(@Param("userId") Integer userId, RowBounds rowBounds);

    /**
     * Method to find Purchase by giftCertificate id.
     * @param giftId to find element in database.
     * @return list.
     */
    @Select("SELECT purchaseDate, price, user_id, giftcertificates_id FROM "
            + "user_giftcertificates WHERE giftcertificates_id = #{giftId}")
    @Results(value = {
            @Result(property="purchaseDate", column = "purchaseDate"),
            @Result(property="userId", column="user_id"),
            @Result(property="giftCertificateId", column = "giftcertificates_id"),
    })
    List<Purchase> findByGiftCertificateId(Integer giftId);

    /**
     * Method to delete userGiftCertificate.
     * @param userGiftCertificate object to find element in database and delete element.
     */
    @Delete("DELETE FROM user_giftcertificates WHERE user_id = #{userId} "
            + "AND giftcertificates_id = #{giftCertificateId}")
    void delete(Purchase userGiftCertificate);
}
