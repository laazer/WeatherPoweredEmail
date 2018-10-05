package com.laazer.wpe.internal.exception;

/**
 * Exception thrown when a bean fails to initialize.
 *
 * Created by Laazer
 */
public class DuplicateEntityException extends AbstractFormatException {

    public DuplicateEntityException(Throwable e, String message, String... args) {
        super(e, message, args);
    }

    public DuplicateEntityException(String message, String... args) {
        super(message, args);
    }
}
