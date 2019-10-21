package com.epam.esm.sqlgenerator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class SqlGeneratorTest {

    private Map<String, Object> map;
    private SqlGenerator sqlGenerator;

    @Before
    public void init() {
        sqlGenerator = new SqlGenerator();
        map = new LinkedHashMap<>();
    }

    @Test
    public void getSqlSearchQueryByTagNameTest() {
        final String expected = "SELECT giftcertificates.id, giftcertificates.name, giftcertificates.description, "
                + "giftcertificates.price, giftcertificates.createDate, giftcertificates.lastUpdateDate, "
                + "giftcertificates.duration FROM giftcertificates JOIN "
                + "tag_giftcertificates ON giftcertificates.id = tag_giftcertificates."
                + "giftcertificates_id JOIN tag ON tag.id = tag_giftcertificates.tag_id "
                + "where tag.name in(#{first}) ORDER BY giftcertificates.name ";
        map.put("tagName", "first");
        map.put("orderBy", "name");
        Assert.assertEquals(expected, sqlGenerator.generateSortQuery(map));
    }

    @Test
    public void getSqlSearchQueryByNameTest() {
        final String expected = "SELECT giftcertificates.id, giftcertificates.name, giftcertificates.description, "
                + "giftcertificates.price, giftcertificates.createDate, giftcertificates.lastUpdateDate, "
                + "giftcertificates.duration FROM giftcertificates JOIN "
                + "tag_giftcertificates ON giftcertificates.id = tag_giftcertificates."
                + "giftcertificates_id JOIN tag ON tag.id = tag_giftcertificates.tag_id "
                + "where giftcertificates.name LIKE #{partOfName} ORDER BY giftcertificates.createDate  desc";
        map.put("partOfName", "%fir%");
        map.put("orderBy", "createDate,desc");
        Assert.assertEquals(expected.length(), sqlGenerator.generateSortQuery(map).length());
    }

    @Test
    public void getSqlSearchQueryByDescriptionTest() {
        final String expected = "SELECT giftcertificates.id, giftcertificates.name, giftcertificates.description, "
                + "giftcertificates.price, giftcertificates.createDate, giftcertificates.lastUpdateDate, "
                + "giftcertificates.duration FROM giftcertificates JOIN "
                + "tag_giftcertificates ON giftcertificates.id = tag_giftcertificates."
                + "giftcertificates_id JOIN tag ON tag.id = tag_giftcertificates.tag_id "
                + "where giftcertificates.description LIKE #{partDescription} and tag.name in(#{first}) "
                + "ORDER BY giftcertificates.lastUpdateDate ";
        map.put("partDescription", "%fir%");
        map.put("tagName", "first");
        map.put("orderBy", "lastUpdateDate, desc");
        Assert.assertEquals(expected, sqlGenerator.generateSortQuery(map));
    }

    @After
    public void destroy() {
        map = null;
        sqlGenerator = null;
    }
}
