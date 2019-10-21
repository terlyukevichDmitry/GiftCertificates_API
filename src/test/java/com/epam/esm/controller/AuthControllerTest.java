package com.epam.esm.controller;

import com.epam.esm.config.ApplicationConfig;
import com.epam.esm.security.entity.LoginRequest;
import com.epam.esm.service.UserService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@TestPropertySource("classpath:testConfig.properties")
@SpringBootTest(classes = UserService.class)
@Sql({"classpath:drop.sql", "classpath:ddl.sql", "classpath:dml.sql"})
public class AuthControllerTest{

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void authenticateUserTest() {
        final String expected = "{\"accessToken\":\"eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MywidXNlcm5hbWUiOiJ0aGlyZExvZ2luIiwi"
                + "cm9sZSI6IkFETUlOIn0.xkN1B0GDNLjFIrtFlPv2t3deygD4YgcN5CYTj9SyfauYmMSSZGDvEwrFUmqdA4IgcSH29eEWucU1mrD5"
                + "-fwKDw\",\"tokenType\":\"bearer\"}";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("thirdLogin");
        loginRequest.setPassword("thirdPassword");
        RestAssuredMockMvc
                .given()
                .body(loginRequest)
                .contentType(ContentType.JSON)
                .header("lang", "en-US")
                .webAppContextSetup(webApplicationContext)
                .when()
                .post("/api/auth/signin")
                .then()
                .statusCode(200)
                .body(equalTo(expected));
    }
}
