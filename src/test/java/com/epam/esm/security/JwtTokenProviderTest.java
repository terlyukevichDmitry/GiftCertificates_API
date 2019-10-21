package com.epam.esm.security;

import com.epam.esm.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:testConfig.properties")
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)
@SpringBootTest(classes = JwtTokenProvider.class)
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void validateTokenFirstTest() {
        jwtTokenProvider.validateToken("sdf");
    }
}
