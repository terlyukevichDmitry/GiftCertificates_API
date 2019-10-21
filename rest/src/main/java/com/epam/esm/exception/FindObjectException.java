package com.epam.esm.exception;

/**
 * The class to check exception situations.
 * @author  Terliukevich Dzmitry
 * @see     java.lang.Error
 * @since   JDK1.0
 */
public class FindObjectException extends Exception {
    /**
     * Constructor without parameters.
     */
    public FindObjectException() {
        super();
    }

    /**
     * Constructor with parameters.
     */
    public FindObjectException(String message) {
        super(message);
    }
}
