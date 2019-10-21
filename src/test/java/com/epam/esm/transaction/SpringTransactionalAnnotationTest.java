package com.epam.esm.transaction;

import com.epam.esm.config.ApplicationConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:testConfig.properties")
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)
@SpringBootTest(classes = GiftCertificateService.class)
public class SpringTransactionalAnnotationTest {

    private Logger LOGGER = LogManager.getLogger(SpringTransactionalAnnotationTest.class);

    @Rule
    public TestName testName = new TestName();

    @Autowired
    private GiftCertificateService service;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeTransaction
    public void beforeTransactionInitData() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("Updated description: Put on a happy face.");
        giftCertificate.setPrice(11.0);
        giftCertificate.setDuration(11);
        giftCertificate.setName("updatedName");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("newTagOfCreateGift"));
        giftCertificate.setTags(tags);
        service.create(giftCertificate);
    }

    @Before
    public void printTestName() {
        LOGGER.info("Test: " + testName.getMethodName());
    }

    @Test
    @Transactional
    public void rollbackDataByDefault() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("Updated description: Put on a happy face.1");
        giftCertificate.setPrice(12.0);
        giftCertificate.setDuration(15);
        giftCertificate.setName("updatedName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("newTagOfCreateGift1"));
        giftCertificate.setTags(tags);
        LOGGER.info("Is flagged for rollback? "
                + TestTransaction.isFlaggedForRollback());
        service.create(giftCertificate);
    }

    @Test
    @Transactional
    public void commitData() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("Updated description: Put on a happy face.1");
        giftCertificate.setPrice(12.0);
        giftCertificate.setDuration(15);
        giftCertificate.setName("updatedName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("newTagOfCreateGift1"));
        giftCertificate.setTags(tags);
        LOGGER.info("Is transaction active? "
                + TestTransaction.isActive());
        service.create(giftCertificate);
        TestTransaction.flagForCommit();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void rollbackFalse() {

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("Updated description: Put on a happy face.1");
        giftCertificate.setPrice(12.0);
        giftCertificate.setDuration(15);
        giftCertificate.setName("updatedName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("newTagOfCreateGift1"));
        giftCertificate.setTags(tags);
        LOGGER.info("Is flagged for rollback? "
                + TestTransaction.isFlaggedForRollback());
        service.create(giftCertificate);
    }

    @AfterTransaction
    public void afterTransactionVerifyRows() {
        String methodName = testName.getMethodName();
        if ("commitData".equals(methodName)) {
            assertEquals(8, countEmpRows());
        } else if ("rollbackDataByDefault".equals(methodName)) {
            assertEquals(4, countEmpRows());
        } else if ("rollbackFalse".equals(methodName)) {
            assertEquals(6, countEmpRows());
        }
    }

    private int countEmpRows() {
        List<GiftCertificate> empNames = service.findSortQuery(new HashMap<>());
        return empNames.size();
    }
}
