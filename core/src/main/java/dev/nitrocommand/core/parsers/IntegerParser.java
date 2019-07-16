package dev.nitrocommand.core.parsers;

import dev.nitrocommand.core.ArgumentParser;


public class IntegerParser implements ArgumentParser<Integer> {
    @Override
    public Integer parse(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public Class<?> getType() {
        return int.class;
    }
}
