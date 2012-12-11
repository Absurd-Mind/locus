package org.inrain.pmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Util {
    public static Logger createLogger() {
        return LoggerFactory.getLogger(new Throwable().getStackTrace()[1].getClassName());
    }
}
