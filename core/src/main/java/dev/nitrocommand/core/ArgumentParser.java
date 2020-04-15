package dev.nitrocommand.core;

import dev.nitrocommand.core.exceptions.ArgumentParserException;

public interface ArgumentParser<T> {

    T parse(String s) throws ArgumentParserException;

    Class<?> getType();
}
