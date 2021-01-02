package dev.nitrocommand.core;

import dev.nitrocommand.core.exceptions.ArgumentParserException;

/**
 * ArgumentParser is a object that allows non String objects into the method.
 *
 * @param <T> the object type
 * @since 1.0
 */
public interface ArgumentParser<T> {
    /**
     * Parse the argument into the type you are generating.
     *
     * @param s the argument un parsed.
     * @return the parsed argument.
     * @throws ArgumentParserException thrown when an argument fails to parse.
     */
    T parse(String s) throws ArgumentParserException;

    /**
     * The Class type for the argument.
     *
     * @return class type for argument..
     */
    Class<?> getType();
}
