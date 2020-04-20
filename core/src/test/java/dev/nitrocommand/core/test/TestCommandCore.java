package dev.nitrocommand.core.test;

import dev.nitrocommand.core.CommandParser;
import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.Utils;
import dev.nitrocommand.core.basic.BasicCommandCore;

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        System.out.println(base);
        String parsedMessage = message.substring(base.length() + 1);
        System.out.println(parsedMessage);
        NitroCommandObject object = getCommand(base);
        assertNotNull(object);
        NitroSubCommand command = CommandParser.locateSubCommand(parsedMessage, object);
        assertNotNull(object);
        Utils.executeCommand(command, Utils.getArguments(parsedMessage, message, command, command.method().getParameters(), new Object[0], this));
    }
}
