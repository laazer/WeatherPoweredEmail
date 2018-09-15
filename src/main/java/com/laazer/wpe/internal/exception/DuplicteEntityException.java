package com.laazer.wpe.internal.exception;

/**
 * Exception thrown when a bean fails to initialize.
 *
 * Created by Laazer
 */
public class DuplicteEntityException extends Exception {

    public DuplicteEntityException(final String message, final Throwable e) {
        super(message, e);
    }
}
