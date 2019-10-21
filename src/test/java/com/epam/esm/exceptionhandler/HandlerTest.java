package com.epam.esm.exceptionhandler;

import com.epam.esm.config.ApplicationConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@SpringBootTest(classes = Handler.class)
public class HandlerTest {

    @Autowired
    private Handler handler;

    @Test
    public void getInternalErrorTest() {
        final String expected = "The execution of the service failed in some way.";
        ResponseEntity<ErrorMessage> responseEntity
                = handler.getInternalError(null);
        Assert.assertEquals(expected,
                Objects.requireNonNull(responseEntity.getBody()).getErrorMessages().get("message").toString());
    }

    @Test
    public void getAccessDeniedExceptionTest() {
        final String expected = "Access is denied.";
        ResponseEntity<ErrorMessage> responseEntity
                = handler.getAccessDeniedException(null, new AccessDeniedException("message"));
        Assert.assertEquals(expected,
                Objects.requireNonNull(responseEntity.getBody()).getErrorMessages().get("message").toString());
    }
}
