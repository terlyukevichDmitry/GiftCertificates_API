package com.epam.esm.abstractrow;

import com.epam.esm.entity.Entity;
import org.apache.ibatis.session.RowBounds;

/**
 * This class has one method which need to create rowBounds object to create pagination in application.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public abstract class AbstractRowAction {
    /**
     * Method to create rowBounds object;
     * @param page number of pages.
     * @param limit number of elements on one page.
     * @return object for pagination.
     */
    protected RowBounds generateRowBounds(Integer page, Integer limit) {
        RowBounds rowBounds = new RowBounds();
        if (page != null && limit != null) {
            int offset = (page - 1) * limit;
            rowBounds = new RowBounds(offset, limit);
        }
        return rowBounds;
    }

    /**
     * @param entity to check on null pointer.
     * @return true or false.
     */
    protected boolean checkEntity(Entity entity) {
        return entity == null;
    }
}
