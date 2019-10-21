package com.epam.esm.exceptionhandler;

import java.util.Map;

/**
 * Class for creating error message and getting this to client.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
public class ErrorMessage {
    /**
     * Element.
     */
    private Map<String, Object> errorMessages;
    /**
     * Default constructor.
     */
    ErrorMessage() {
    }

    /**
     * Constructor with parameters.
     * @param errorMessages errors.
     */
    ErrorMessage(Map<String, Object> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Map<String, Object> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Map<String, Object> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" + "errorMessages=" + errorMessages + '}';
    }
}
