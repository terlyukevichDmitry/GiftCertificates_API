package com.epam.esm.mapperinterface;

import com.epam.esm.entity.User;
import com.epam.esm.sqlgenerator.SqlGenerator;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Mapper interface with methods to work's with userGiftCertificate table in database.
 * @author Terliukevich Dzmitry.
 * @since 1.0
 */
@Mapper
public interface UserMapper {
    /**
     * Method to find all element in database.
     * @param rowBounds pagination.
     * @return list.
     */
    @Select("select id, role from user")
    @Results(value = {
            @Result(id = true, property="id", column="id"),
            @Result(property="role", column="role"),
    })
    List<User> findAll(RowBounds rowBounds);

    /**
     * Method to find users by login.
     * @param login to find element in database.
     * @return list.
     */
    @Select("select id, login, password, role from user WHERE login = #{login}")
    @Results(value = {
            @Result(id = true, property="id", column="id"),
            @Result(property="login", column="login"),
            @Result(property="password", column="password"),
            @Result(property="role", column="role"),
    })
    User findByLogin(String login);

    /**
     * Method to find users by userId.
     * @param userId to find element in database.
     * @return list.
     */
    @Select("select id, login, password, role from user WHERE id = #{userId}")
    @Results(value = {
            @Result(id = true, property="id", column="id"),
            @Result(property="login", column="login"),
            @Result(property="password", column="password"),
            @Result(property="role", column="role"),
    })
    User findById(@Param("userId") Integer userId);

    /**
     * Method to create user.
     * @param user object to create element in database.
     */
    @Insert("INSERT into user(login, password, role) VALUES(#{login}, #{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void create(User user);

    /**
     * Method to delete user by id.
     * @param userId to find element in database and delete element.
     */
    @Delete("DELETE FROM user WHERE id = #{userId}")
    int delete(Integer userId);

    /**
     * Method to update user.
     * @param user to update element in database.
     */
    @UpdateProvider(type=SqlGenerator.class, method="generateUpdateUserQuery")
    int update(User user);
}
