package com.epam.esm.service;

import com.epam.esm.config.ApplicationConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:testConfig.properties")
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = GiftCertificateService.class)
public class GiftCertificateServiceTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GiftCertificateService service;

    @Test(expected = UnsupportedOperationException.class)
    public void findAllTest() {
        Assert.assertEquals(3, service.findAll(null, null).size());
    }

    @Test
    public void findByNameTest() {
        final String name = "firstGift";
        Assert.assertEquals(name, service.findByName(name).get(0).getName());
    }

    @Test
    public void getGiftCertificatesWithoutParametersTest() {
        final String expected = "[{\"id\":1,\"name\":\"firstGift\",\"description\":\"first description\",\"price\""
                + ":5.0,\"createDate\":\"2019-04-14T21:07:36Z\",\"lastUpdateDate\":\"2019-04-14T21:07:36Z\",\""
                + "duration\":5,\"tags\":[{\"id\":1,\"name\":\"first\"}]},{\"id\":2,\"name\":\"secondGift\",\""
                + "description\":\"second description\",\"price\":6.0,\"createDate\":\"2019-04-14T21:07:36Z\","
                + "\"lastUpdateDate\":\"2020-04-14T21:07:36Z\",\"duration\":6,\"tags\":[{\"id\":2,\"name\":\""
                + "second\"}]},{\"id\":3,\"name\":\"thirdGift\",\"description\":\"third description\",\"price"
                + "\":7.0,\"createDate\":\"2011-04-14T21:07:36Z\",\"lastUpdateDate\":\"2022-04-14T21:07:36Z\""
                + ",\"duration\":7,\"tags\":[{\"id\":3,\"name\":\"third\"}]}]";
        RestAssuredMockMvc
                .given()
                .webAppContextSetup(context)
                .when()
                .get("/giftCertificates")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void getGiftCertificatesWithParametersTest() {

        final String expected = "[{\"id\":1,\"name\":\"firstGift\",\"description\":\"first description\",\"price\""
                + ":5.0,\"createDate\":\"2019-04-14T21:07:36Z\",\"lastUpdateDate\":\"2019-04-14T21:07:36Z\",\"duration"
                + "\":5,\"tags\":[{\"id\":1,\"name\":\"first\"}]}]";
        RestAssuredMockMvc.
                given().
                webAppContextSetup(context).
                when().
                get("/giftCertificates?tagname=first,second,third&description=fir&name=f&price=5&duration=5"
                        + "&orderby=tagname,asc;price,desc").
                then().
                statusCode(200).
                body(equalTo(expected));
    }

    @Test
    public void getGiftCertificatesByIdTest() {

        final String expected = "{\"id\":1,\"name\":\"firstGift\",\"description\":\"first description\",\"price\""
                + ":5.0,\"createDate\":\"2019-04-14T21:07:36Z\",\"lastUpdateDate\":\"2019-04-14T21:07:36Z\",\"duration"
                + "\":5,\"tags\":[{\"id\":1,\"name\":\"first\"}]}";

        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                when().
                get("/giftCertificates/1").
                then().
                statusCode(200).
                body(equalTo(expected));
    }

    @Test
    public void createGiftCertificatesTest() {

        final String expected = "GiftCertificate is created: 4";

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("Put on a happy face.");
        giftCertificate.setPrice(9.0);
        giftCertificate.setDuration(7);
        giftCertificate.setName("newCertificate");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("newTagOfCreateGift"));
        giftCertificate.setTags(tags);

        RestAssuredMockMvc.given()
                .body(giftCertificate)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWU"
                        + "iOiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROT"
                        + "E1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9K"
                        + "FW5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .post("/giftCertificates")
                .then()
                .statusCode(201)
                .body(equalTo(expected));
    }

    @Test
    public void updateGiftCertificatesTest() {

        final String expected = "GiftCertificate is updated: 3";

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("Updated description: Put on a happy face.");
        giftCertificate.setPrice(11.0);
        giftCertificate.setName("updatedName");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("newTagOfCreateGift"));
        giftCertificate.setTags(tags);

        RestAssuredMockMvc.given()
                .body(giftCertificate)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUiOi"
                        + "JmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE1zTT"
                        + "Z4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KFW5cO6P"
                        + "L45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .put("/giftCertificates/3")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void updatePriceForGiftCertificatesTest() {

        final String expected = "Price for giftCertificate with id = 3 is updated!";
        RestAssuredMockMvc.given()
                .body(1.1)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUi"
                        + "OiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE1"
                        + "zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KFW5"
                        + "cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .patch("/giftCertificates/3")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void deleteGiftCertificatesTest() {

        final String expected = "Number of deleted giftCertificates: 1";
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUi"
                        + "OiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE1"
                        + "zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KFW5"
                        + "cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .delete("/giftCertificates/4")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }
}
