package com.epam.esm.service;

import com.epam.esm.config.ApplicationConfig;
import com.epam.esm.entity.User;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:testConfig.properties")
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService service;

    @Test
    public void findAllTest() {
        Assert.assertEquals(3, service.findAll(0, 100).size());
    }

    @Test
    public void getAllUsersTest() {
        final String expected = "[{\"id\":1,\"login\":null,\"password\":null,\"role\":\"ADMIN\"},{\"id\":2,\"login\":null,\"password\":null,\"role\":\"USER\"},{\"id\":3,\"login\":null,\"password\":null,\"role\":\"ADMIN\"}]";
        RestAssuredMockMvc
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUi"
                        + "OiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE"
                        + "1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KF"
                        + "W5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .webAppContextSetup(context)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void getUserByIdTest() {
        final String expected = "{\"id\":1,\"login\":null,\"password\":null,\"role\":\"ADMIN\"}";
        RestAssuredMockMvc
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUi"
                        + "OiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE"
                        + "1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KF"
                        + "W5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .webAppContextSetup(context)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void getBoughtGiftCertificatesByUserIdTest() {
        final String expected = "[{\"purchaseDate\":\"2019-08-23T09:42:36Z\",\"userId\":2,\"giftCertificateId\":2," +
                "\"price\":6.0}]";
        RestAssuredMockMvc
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MiwidXNlcm5hbW" +
                        "UiOiJzZWNvbmRMb2dpbiIsInJvbGUiOiJVU0VSIn0.ThMzSh_6i469hviDdmPzZpMV1_3J39kmAkxgq1GYbqvFbsf" +
                        "kebZcQC_cdogAo6M-7vb2LJIFrUAttEHE1qgMxQ")
                .webAppContextSetup(context)
                .when()
                .get("/users/purchases")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void getBoughtGiftCertificateByIdAndByUserIdTest() {
        final String expected = "[{\"purchaseDate\":\"2019-08-23T09:42:36Z\",\"userId\":1,\"giftCertificateId\":1,\"price\":5.0}]";
        RestAssuredMockMvc
                .given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUi"
                        + "OiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE"
                        + "1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KF"
                        + "W5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .webAppContextSetup(context)
                .when()
                .get("/users/purchases/1")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void createUserTest() {
        final String expected = "User registered successfully: 4";

        User user = new User();
        user.setLogin("newTestLogin");
        user.setPassword("newTestPassword");
        user.setRole("USER");

        RestAssuredMockMvc.given()
                .body(user)
                .contentType(ContentType.JSON)
                .webAppContextSetup(context)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body(equalTo(expected));
    }

    @Test
    public void updateUserTest() {
        final String expected = "User is updated: 3";

        User user = new User();
        user.setLogin("newUpdatedTestLogin");
        user.setPassword("newUpdatedTestPassword");

        RestAssuredMockMvc.given()
                .body(user)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MiwidXNlcm5hbW"
                        + "UiOiJzZWNvbmRMb2dpbiIsInBhc3N3b3JkIjoiJDJhJDEwJGxpSDFxVDhEalFsRXo5L1BwTVdFVC5kRzM0MXVpOF"
                        + "gvSmxjRkxXUTN5MlFLZnI0TUVqM2FDIiwicm9sZSI6IlVTRVIifQ.8KhfvrWAXq2NoL_smZD-FSLSOXWp7zVGzb0"
                        + "nFoYn6t01trAYL0kbQ6bZTDStiqNhNPvOzXZoRCXL-MbOYTHe6w")
                .header("lang", "en-US")
                .contentType(ContentType.JSON)
                .webAppContextSetup(context)
                .when()
                .put("/users/3")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void deleteUserTest() {
        final String expected = "Number of deleted users: 1";
        RestAssuredMockMvc.given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWU"
                        + "iOiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0RO"
                        + "TE1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr"
                        + "9KFW5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .contentType(ContentType.JSON)
                .webAppContextSetup(context)
                .when()
                .delete("/users/4")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void buyGiftCertificateForUserTest() {
        final String expected = "GiftCertificate for user is bought: 2";
        RestAssuredMockMvc.given()
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUi"
                        + "OiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE"
                        + "1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KF"
                        + "W5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .contentType(ContentType.JSON)
                .webAppContextSetup(context)
                .when()
                .post("/users/giftCertificates/3")
                .then()
                .statusCode(201)
                .body(equalTo(expected));
    }
}
