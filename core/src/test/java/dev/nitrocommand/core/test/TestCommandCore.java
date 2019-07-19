package dev.nitrocommand.core.test;

import dev.nitrocommand.core.CommandParser;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.Utils;
import dev.nitrocommand.core.basic.BasicCommandCore;

import java.io.PrintStream;
import java.util.Arrays;

public class TestCommandCore extends BasicCommandCore<PrintStream> {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public void sendMessage(PrintStream senderObject, String message) {
        senderObject.println(message);
    }

    public void execute(String s) {
        executeCommand(s);
    }

    private void executeCommand(String message) {
        String base = message.split(" ")[0];
        NitroSubCommand command = CommandParser.locateSubCommand(message.substring(base.length()), getCommand(base));
        if (command == null) {
            return;
        }

        Utils.executeCommand(command, Utils.getArguments(message, command, command.method().getParameters(), new Object[0], this));
    }
}
