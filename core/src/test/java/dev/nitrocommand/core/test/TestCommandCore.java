package dev.nitrocommand.core.test;

import dev.nitrocommand.core.basic.BasicCommandCore;

public class TestCommandCore extends BasicCommandCore {
    @Override
    public String getName() {
        return "test";
    }

    public void execute(String s) {
        executeCommand(s);
    }
}
