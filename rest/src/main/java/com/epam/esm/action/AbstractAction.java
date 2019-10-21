package com.epam.esm.action;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.FindObjectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public abstract class AbstractAction {

    /**
     * Tag resource code.
     */
    private static final String tagRequestedResourceId = "01";
    /**
     * GiftCertificate resource code.
     */
    private static final String giftCertificatesResourceId = "02";
    /**
     * User resource code.
     */
    private static final String usersRequestedResourceId = "03";
    /**
     * Default resource code.
     */
    private static final String defaultRequestedResourceId = "04";

    /**
     * @param request object.
     * @return language, which we'll use to find sentence on correct language in resource bundle.
     */
    protected String checkLang(HttpServletRequest request) {
        String lang = "en-US";
        if (request != null && request.getHeader("lang") != null) {
            lang = request.getHeader("lang");
        }
        return lang;
    }

    /**
     * @param entity to check on null pointer.
     * @return true or false.
     */
    protected boolean checkEntity(Entity entity) {
        return entity == null;
    }

    /**
     * @param entity object.
     */
    protected void checkEntityExistence(Entity entity) throws FindObjectException {
        if (checkEntity(entity)) {
            throw new FindObjectException();
        }
    }

    /**
     * @param entities list.
     */
    protected void checkList(List<? extends Entity> entities) throws FindObjectException {
        if (entities.isEmpty()) {
            throw new FindObjectException();
        }
    }

    /**
     * @param resource resource endpoint.
     * @return code status.
     */
    protected String getRequestedResourceId(String resource) {
        String requestedResourceId;
        switch (resource) {
            case "tags": requestedResourceId = tagRequestedResourceId;
                break;
            case "giftCertificates": requestedResourceId = giftCertificatesResourceId;
                break;
            case "users": requestedResourceId = usersRequestedResourceId;
                break;
            default: requestedResourceId = defaultRequestedResourceId;
        }
        return requestedResourceId;
    }

    /**
     * @param request to get path.
     * @return paths.
     */
    protected String getResourcePath(HttpServletRequest request) {
        if (request != null) {
            String[] paths = request.getServletPath().split("/");
            return paths[1];
        }
        return "";
    }
}
