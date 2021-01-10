package dev.nitrocommand.core;

import java.lang.reflect.Method;

public interface NitroTabCompleter {
    /**
     * A list of formats for this NitroSubCommand
     *
     * @return the formats
     */
    String variable();

    /**
     * The method name
     *
     * @return the method name
     */
    String methodName();

    /**
     * The actual method
     *
     * @return the actual method
     */
    Method method();

    /**
     * The command object for this sub command
     *
     * @return the command object
     */
    NitroCommandObject command();

}
