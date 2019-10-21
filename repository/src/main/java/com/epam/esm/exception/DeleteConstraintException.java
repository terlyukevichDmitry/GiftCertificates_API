package com.epam.esm.exception;

/**
 * The class to check exception situations.
 * @author  Terliukevich Dzmitry
 * @see     java.lang.Error
 * @since   JDK1.0
 */
public class DeleteConstraintException extends Exception {
    /**
     * Constructor with parameters.
     */
    public DeleteConstraintException(String message) {
        super(message);
    }
}
