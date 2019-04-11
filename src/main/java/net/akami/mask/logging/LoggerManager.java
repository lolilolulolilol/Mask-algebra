package net.akami.mask.logging;


import org.slf4j.event.Level;

/**
 * Class used to manage logging through the api. Allows logging disabling and logger selection by class type.
 */
public class LoggerManager {

    private LoggerManager(){}

    /**
     * Gets all the current loggers and sets their level to {@link Level#OFF}
     */
    public static void  disableAll() {
        //setLevelAll(org.slf4j.event.Level.OFF);
    }

    /**
     * Sets all the current loggers' level to the level given
     * @param level the level given
     */
    public static void setLevelAll(Level level) {

    }
}
