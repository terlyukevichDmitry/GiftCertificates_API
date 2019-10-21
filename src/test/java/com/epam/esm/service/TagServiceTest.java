package com.epam.esm.service;

import com.epam.esm.config.ApplicationConfig;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@TestPropertySource("classpath:testConfig.properties")
@FixMethodOrder
@SpringBootTest(classes = TagService.class)
public class TagServiceTest {

    @Autowired
    public TagService tagService;

    @Autowired
    private WebApplicationContext context;

    private final static String name = "testName";

    @Test
    public void findAllTest() {
        Assert.assertEquals(4, tagService.findAll(0, 100).size());
    }

    @Test
    public void findByNameTest() {
        final String name = "first";
        Assert.assertEquals(name, tagService.findByName(name).getName());
    }

    @Test
    public void getTagByIdTest() {
        final String expected = "{\"id\":1,\"name\":\"first\"}";
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbW"
                        + "UiOiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0R"
                        + "OTE1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SW"
                        + "r9KFW5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .get("/tags/1")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void getTagsTest() {
        final String expected = "[{\"id\":1,\"name\":\"first\"},{\"id\":2,\"name\":\"second\"},{\"id\":3,\"name\":\""
                + "third\"},{\"id\":4,\"name\":\"newTagOfCreateGift\"}]";
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWUiO"
                        + "iJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROTE1zT"
                        + "TZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9KFW5cO6"
                        + "PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .get("/tags")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void getPopularTagTest() {
        final String expected = "{\"id\":4,\"name\":\"newTagOfCreateGift\"}";
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbWU"
                        + "iOiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0ROT"
                        + "E1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SWr9K"
                        + "FW5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .get("/tags/popular")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test
    public void createTest() {
        final String expected = "Tag is created: 5";
        TagDto tagDto = new TagDto();
        tagDto.setName(name);
        RestAssuredMockMvc
                .given()
                .body(tagDto)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbW"
                        + "UiOiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0R"
                        + "OTE1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SW"
                        + "r9KFW5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")
                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .post("/tags")
                .then()
                .statusCode(201)
                .body(equalTo(expected));
    }

    @Test
    public void deleteTest() {
        final String expected = "Number of deleted tags: 1";

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwidXNlcm5hbW"
                        + "UiOiJmaXJzdExvZ2luIiwicGFzc3dvcmQiOiIkMmEkMTAkWmwvdFJBMnBlUE02NFBiMmVzZTBpdTFEdXQ5TXoxZ0R"
                        + "OTE1zTTZ4QXEzQTZhVTJnQ1ZoTzYiLCJyb2xlIjoiQURNSU4ifQ.RPi2GetnwJOK5DQZsRa9MZX0RIrHpYAwJM2SW"
                        + "r9KFW5cO6PL45ULOKTu4YB5_kJXOSDDgMZSXqUvOAYZ7TA-LA")

                .header("lang", "en-US")
                .webAppContextSetup(context)
                .when()
                .delete("/tags/5")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateNegativeTest() throws UnsupportedOperationException{
        tagService.update(new Tag(3, "third"));
    }
}
