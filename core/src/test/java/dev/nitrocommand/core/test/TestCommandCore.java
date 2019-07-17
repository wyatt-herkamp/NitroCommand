package dev.nitrocommand.core.test;

import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.Utils;
import dev.nitrocommand.core.basic.BasicCommandCore;

import java.util.Arrays;

public class TestCommandCore extends BasicCommandCore {
    @Override
    public String getName() {
        return "test";
    }

    public void execute(String s) {
        executeCommand(s);
    }
    private void executeCommand(String message) {
        NitroSubCommand command = parser.locateCommand(message);
        if (command == null) {
            return;
        }

        parser.executeCommand(command, Utils.getArguments(message, command, command.method().getParameters(), new Object[0], this));

    }
}
