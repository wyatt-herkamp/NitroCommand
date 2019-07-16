package dev.nitrocommand.core;

public interface ArgumentParser<T> {

    T parse(String s);

    Class<?> getType();
}
