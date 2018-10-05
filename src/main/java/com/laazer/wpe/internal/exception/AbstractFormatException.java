package com.laazer.wpe.internal.exception;

import java.text.MessageFormat;

/**
 * Created by Laazer
 */
public abstract class AbstractFormatException extends Exception {
    public AbstractFormatException(final Throwable e,
                                   final String message,
                                   final String... args) {
        super(new MessageFormat(message).format(args), e);
    }

    public AbstractFormatException(final String message,
                                   final String... args) {
        super(new MessageFormat(message).format(args));
    }
}
