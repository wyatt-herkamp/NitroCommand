package dev.nitrocommand.core.parsers;

import dev.nitrocommand.core.ArgumentParser;
import dev.nitrocommand.core.exceptions.ArgumentParserException;


public class IntegerParser implements ArgumentParser<Integer> {


    @Override
    public Integer parse(String s) throws ArgumentParserException {
        return Integer.parseInt(s);
    }

    @Override
    public Class<?> getType() {
        return int.class;
    }
}
