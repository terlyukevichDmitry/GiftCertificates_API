package com.epam.esm.sqlgenerator;

import com.epam.esm.entity.User;

import java.util.*;

/**
 * This class we use to generate sql query with different parameters.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public class SqlGenerator {

    /**
     * StringBuilder object.
     */
    private StringBuilder giftCertificateQuery;

    /**
     * Final element.
     */
    private static final String joinTablesQuery = "SELECT giftcertificates.id, giftcertificates.name, "
            + "giftcertificates.description, giftcertificates.price, giftcertificates.createDate, "
            + "giftcertificates.lastUpdateDate, giftcertificates.duration FROM giftcertificates "
            + "JOIN tag_giftcertificates ON giftcertificates.id = tag_giftcertificates."
            + "giftcertificates_id JOIN tag ON tag.id = tag_giftcertificates.tag_id ";

    private static final String updateUserQuery = "UPDATE user SET login=#{login}, password=#{password}";

    /**
     * Method to generate sql query to search elements with different parameters.
     * @param queryElements element to generate query.
     * @return string query.
     */
    private String generateSearchQuery(Map<String, Object> queryElements) {
        giftCertificateQuery = new StringBuilder(joinTablesQuery);

        Map<String, Object> tagElements = new LinkedHashMap<>();
        if (queryElements.size() != 0) {
            int counter = 0;
            for (String queryElement : queryElements.keySet()) {
                if (("tagName").equals(queryElement)) {
                    andChecker(counter, giftCertificateQuery);
                    whereChecker(counter, giftCertificateQuery);
                    giftCertificateQuery.append("tag.name in(");
                    generateTagQuery(queryElements, queryElement, giftCertificateQuery, tagElements);
                    giftCertificateQuery.append(")");
                    counter++;
                } else if (("partOfName").equals(queryElement)) {
                    andConditions(giftCertificateQuery, counter, queryElement,
                            "giftcertificates.name LIKE #{");
                    counter++;
                } else if (("partDescription").equals(queryElement)) {
                    andConditions(giftCertificateQuery, counter, queryElement,
                            "giftcertificates.description LIKE #{");
                    counter++;
                } else if (("price").equals(queryElement)) {
                    andConditions(giftCertificateQuery, counter, queryElement,
                            "giftcertificates.price = #{");
                    counter++;
                } else if (("duration").equals(queryElement)) {
                    andConditions(giftCertificateQuery, counter, queryElement,
                            "giftcertificates.duration = #{");
                    counter++;
                }
            }
        }
        for (Map.Entry<String, Object> tagElement: tagElements.entrySet()) {
            queryElements.put(tagElement.getKey(), tagElement.getValue());
        }
        return giftCertificateQuery.toString();
    }

    private void generateTagQuery(Map<String, Object> queryElements, String queryElement,
                                  StringBuilder query, Map<String, Object> tagElements) {
        String tags = (String) queryElements.get(queryElement);
        String[] severalTags = tags.split(",");
        for (String tagName : severalTags) {
            tagElements.put(tagName, tagName);
            query.append("#{").append(tagName).append("}");
            if (!tagName.equals(severalTags[severalTags.length - 1])) {
                query.append(",");
            }
        }
    }

    /**
     * Method to generate sql query for sort elements with different parameters.
     * @param queryElements element to generate query.
     * @return string query.
     */
    public String generateSortQuery(Map<String, Object> queryElements) {
        giftCertificateQuery = new StringBuilder(generateSearchQuery(queryElements));
        if (queryElements.size() != 0) {
            int counter = 0;
            for (Map.Entry<String, Object> queryElement :queryElements.entrySet()) {
                if (("orderBy").equals(queryElement.getKey())) {
                    StringBuilder sortQuery = new StringBuilder();
                    sortQuery.append(" ORDER BY ");
                    for (String sortValue :String.valueOf(queryElement.getValue()).split(";")) {
                        boolean checker = false;
                        if (counter != 0) {
                            sortQuery.append(", ");
                        }
                        for (String sortElement: sortValue.split(",")) {
                            if (checkValues(sortElement)) {
                                sortQuery.append("giftcertificates.").append(sortElement).append(" ");
                                counter++;
                                checker = true;
                            } else if(checker && (("asc").equals(sortElement) || ("desc").equals(sortElement))) {
                                sortQuery.append(" ").append(sortElement);
                                counter++;
                            }
                        }
                    }
                    giftCertificateQuery.append(sortQuery);
                }
            }
        }
        return giftCertificateQuery.toString();
    }

    public String generateUpdateUserQuery(User user) {
        StringBuilder updateQuery = new StringBuilder(updateUserQuery);
        if (user.getRole() != null) {
            updateQuery.append(",role=#{role}");
        }
        updateQuery.append(" WHERE id=#{id}");
        return updateQuery.toString();
    }

    private void andConditions(StringBuilder query, final int counter, String queryElement, String sqlCondition) {
        andChecker(counter, query);
        whereChecker(counter, query);
        query.append(sqlCondition).append(queryElement).append("}");
    }

    private boolean checkValues(String elements) {
        return ("name").equals(elements)
                || ("createDate").equals(elements)
                || ("lastUpdateDate").equals(elements)
                || ("price").equals(elements)
                || ("duration").equals(elements);
    }

    private void andChecker(final int counter, StringBuilder query) {
        if (counter != 0) {
            query.append(" and ");
        }
    }

    private void whereChecker(final int counter, StringBuilder query) {
        if (counter == 0) {
            query.append("where ");
        }
    }
}
