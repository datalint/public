package gwt.xml.shared;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DebugUtil {
    private static final Logger logger = Logger.getLogger(DebugUtil.class.getName());

    private DebugUtil() {
    }

    public static void log(Object message) {
        logger.log(Level.INFO, String.valueOf(message));
    }

    public static void log(Throwable caught) {
        logger.log(Level.WARNING, caught.getMessage(), caught);
    }

    public static Logger getLogger() {
        return logger;
    }
}
