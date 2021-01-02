package dev.nitrocommand.core.test.commands;

import dev.nitrocommand.core.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dev.nitrocommand.core.test.TestKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoLoad
@NitroCommand(command = "msg", description = "description", format = "/msg")
public class CMD1 {
    @BaseCommand
    public void baseCommand() {
        System.out.println("Invalid Format");
    }

    @SubCommand(format = "{username} *")
    public void msg(@CommandArgument("username") String username, @CommandArgument("*") String msg) {
        System.out.println(username);
        assertEquals(USERNAME, username);
        assertEquals(MESSAGE, msg);
    }

    @SubCommand(format = "all *")
    public void msgAll(@CommandArgument("*") String msg) {
        assertEquals(MESSAGE_ALL, msg);
    }

    @TabCompleter("username")
    public List<String> getUsernames() {
        return Arrays.stream(USERNAMES).collect(Collectors.toList());
    }
}
