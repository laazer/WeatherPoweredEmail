package com.laazer.wpe.util;

import com.laazer.wpe.internal.exception.BeanInitException;

import java.text.MessageFormat;
import org.slf4j.Logger;

/**
 * Created by Laazer
 */
public final class ExceptionUtil {

    public static void beanInitException(final Logger log, final Throwable e, final String message,
                                         final String... args) throws BeanInitException {
        final String digest = new MessageFormat(message).format(args);
        log.error(digest, e);
        throw new BeanInitException(digest, e);
    }
}
