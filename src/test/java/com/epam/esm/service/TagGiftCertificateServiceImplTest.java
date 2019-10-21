package com.epam.esm.service;

import com.epam.esm.config.ApplicationConfig;
import com.epam.esm.entity.TagGiftCertificate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:testConfig.properties")
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)
@SpringBootTest(classes = TagGiftCertificateService.class)
public class TagGiftCertificateServiceImplTest {

    @Autowired
    private TagGiftCertificateService service;

    private List<TagGiftCertificate> list;

    @Before
    public void init() {
        list = new ArrayList<>();
        list.add(new TagGiftCertificate(1, 1));
        list.add(new TagGiftCertificate(2, 2));
        list.add(new TagGiftCertificate(3, 3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void findAllTest() {
        Assert.assertEquals(list.size(), service.findAll().size());
    }

    @Test
    public void findByGiftIdTest() {
        final Integer tagId = 1;
        final Integer giftId = 1;
        Assert.assertEquals("TagGiftCertificate{tagId=1, giftCertificateId=1}",
                new TagGiftCertificate(tagId, giftId).toString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createTest() {
        final int expected = 4;
        final Integer tagId = 1;
        final Integer giftId = 2;
        service.create(new TagGiftCertificate(tagId, giftId));
        Assert.assertEquals(expected, service.findAll().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deleteTest() {
        final int expected = 3;
        final Integer tagId = 1;
        final Integer giftId = 2;
        service.delete(new TagGiftCertificate(tagId, giftId));
        Assert.assertEquals(expected, service.findAll().size());
    }

    @Test
    public void compareTest() {
        TagGiftCertificate tagGiftCertificate = new TagGiftCertificate();
        tagGiftCertificate.setTagId(1);
        tagGiftCertificate.setGiftCertificateId(1);
        Assert.assertEquals(tagGiftCertificate, list.get(0));
    }

    @Test
    public void toStringTest() {
        final String expected = "TagGiftCertificate{tagId=1, giftCertificateId=1}";
        Assert.assertEquals(expected, list.get(0).toString());
    }

    @After
    public void destroy() {
        list = null;
    }
}
