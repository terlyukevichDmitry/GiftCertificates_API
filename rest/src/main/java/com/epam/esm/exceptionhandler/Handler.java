package com.epam.esm.exceptionhandler;

import com.epam.esm.action.AbstractAction;
import com.epam.esm.exception.*;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Class to generate and send message to client that we have exception on this moment.
 * @author Terliukevich Dzmitry
 * @since 1.0
 */
@ControllerAdvice
public class Handler extends AbstractAction {

    /**
     * To manipulating our resource bundle.
     */
    private ResourceBundleMessageSource messageSource;

    /**
     * badRequestErrorCode
     */
    private static final Integer badRequestErrorCode = 400;
    /**
     * forbiddenErrorCode
     */
    private static final Integer forbiddenErrorCode = 403;
    /**
     * internalServerErrorCode
     */
    private static final Integer internalServerErrorCode = 500;
    /**
     * notSupportedErrorCode
     */
    private static final Integer notSupportedErrorCode = 405;

    /**
     * Constructor to init this class with spring.
     *
     * @param messageSource to init.
     */
    public Handler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

        /**
     * Method to get answer on 500 status exception.
     *
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<ErrorMessage> getInternalError(HttpServletRequest request) {
        Map<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("message", messageSource.getMessage("500", new Object[]{},
                Locale.forLanguageTag(checkLang(request))));
        exceptionMessages.put("code", internalServerErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("timestamp", new Date());
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to get answer on 400 status exception.
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> getMethodArgumentNotValidException(HttpServletRequest request,
                                                                    MethodArgumentNotValidException
                                                                                         exception) {
        Map<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", badRequestErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("errors", exception.getMessage().split(";"));
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.BAD_REQUEST);
    }
    /**
     * Method to get answer on 400 status exception.
     *
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorMessage> getHttpMessageNotReadableException(HttpServletRequest request,
                                                                                 HttpMessageNotReadableException
                                                                                         exception) {
        Map<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("message", messageSource.getMessage("400", new Object[]{},
                Locale.forLanguageTag(checkLang(request))));
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", badRequestErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("errors", Objects.requireNonNull(exception.getMessage()).split(";"));
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to get answer on 403 status exception.
     *
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public final ResponseEntity<ErrorMessage> getAccessDeniedException(HttpServletRequest request,
                                                                       AccessDeniedException exception) {

        HashMap<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", forbiddenErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("message", messageSource.getMessage("403", new Object[]{},
                Locale.forLanguageTag(checkLang(request))));
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.FORBIDDEN);
    }

    /**
     * Method to get answer on 400 status exception.
     *
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadCredentialsException.class)
    public final ResponseEntity<ErrorMessage> getBadCredentialsException(HttpServletRequest request,
                                                                         BadCredentialsException exception) {
        HashMap<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", badRequestErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("message", exception.getMessage());
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to get answer on 400 status exception.
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DeleteConstraintException.class)
    public final ResponseEntity<ErrorMessage> getDeleteConstraintException(HttpServletRequest request,
                                                                           DeleteConstraintException exception) {
        HashMap<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", badRequestErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("message", messageSource.getMessage("delete", new Object[]{},
                Locale.forLanguageTag(checkLang(request))) + exception.getMessage());
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to get answer on 405 status exception.
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<ErrorMessage> getHttpRequestMethodNotSupportedException(
            HttpServletRequest request, HttpRequestMethodNotSupportedException exception) {
        HashMap<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", notSupportedErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("message", exception.getMessage());
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Method to get answer on 404 status exception.
     * @param exception object.
     * @return response entity.
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = FindObjectException.class)
    public final @ResponseBody ResponseEntity<HttpStatus> getFindObjectException(FindObjectException exception) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        HashMap<String, Object> exceptionMessages = new LinkedHashMap<>();
        exceptionMessages.put("timestamp", new Date());
        exceptionMessages.put("code", notSupportedErrorCode + getRequestedResourceId(getResourcePath(request)));
        exceptionMessages.put("message", exception.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorMessage(exceptionMessages), HttpStatus.BAD_REQUEST);
    }
}
