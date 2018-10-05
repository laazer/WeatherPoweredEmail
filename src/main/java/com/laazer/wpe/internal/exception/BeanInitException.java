package com.laazer.wpe.internal.exception;

/**
 * Exception thrown when a bean fails to initialize.
 *
 * Created by Laazer
 */
public class BeanInitException extends AbstractFormatException {

    public BeanInitException(String message, String... args) {
        super(message, args);
    }

    public BeanInitException(Throwable e, String message, String... args) {
        super(e, message, args);
    }
}
