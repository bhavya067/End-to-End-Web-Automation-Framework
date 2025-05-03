package base;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.StandardLevel;
import org.openqa.selenium.NoSuchElementException;

public class CustomLogger {

    public static final Level PASS = Level.forName("PASS", StandardLevel.INFO.intLevel() + 1);
    public static final Level FAIL = Level.forName("FAIL", StandardLevel.ERROR.intLevel() + 1);
    public static final Level SKIP = Level.forName("SKIP", StandardLevel.INFO.intLevel() + 1);
    public static final Level error = Level.forName("ERROR", StandardLevel.INFO.intLevel() + 1);
    private Logger logger;

    private CustomLogger(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public static CustomLogger getLogger(Class<?> clazz) {
        return new CustomLogger(clazz);
    }

    public void pass(String message) {
        logger.log(PASS, message);
    }

    public void fail(String message) {
        logger.log(FAIL, message);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }
    
    public void skip(String message) {
        logger.log(SKIP, message);
    }
    
    public void error(String message) {
        logger.log(error, message);
    }


    // Add other methods if needed
}
