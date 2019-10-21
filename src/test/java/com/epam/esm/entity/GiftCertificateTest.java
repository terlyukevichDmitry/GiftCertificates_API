package com.epam.esm.entity;

import org.junit.Assert;
import org.junit.Test;

public class GiftCertificateTest {

    @Test
    public void equalsNegativeTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Assert.assertNotEquals(giftCertificate.toString(), new Tag().toString());
    }

    @Test
    public void equalsNegativeNullTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Assert.assertNotEquals(null, giftCertificate);
    }
}
