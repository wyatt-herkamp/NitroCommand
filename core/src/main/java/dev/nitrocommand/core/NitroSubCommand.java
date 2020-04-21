package dev.nitrocommand.core;

import java.lang.reflect.Method;

/**
 * This represents a sub command
 */
public interface NitroSubCommand {
    /**
     * A list of formats for this NitroSubCommand
     *
     * @return the formats
     */
    String[] formats();

    /**
     * The method name
     *
     * @return
     */
    String methodName();

    /**
     * The actual method
     *
     * @return
     */
    Method method();

    /**
     * The command object for this sub command
     *
     * @return the command object
     */
    NitroCommandObject command();


}
