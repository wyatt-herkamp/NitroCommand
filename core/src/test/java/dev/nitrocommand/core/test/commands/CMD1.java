package dev.nitrocommand.core.test.commands;

import dev.nitrocommand.core.annotations.*;

@AutoLoad
@NitroCommand(command = "msg", description = "description", format = "/msg")
public class CMD1 {
    @BaseCommand
    public void baseCommand() {
        System.out.println("Invalid Format");
    }

    @SubCommand(format = "{username} *")
    public void msg(@CommandArgument(key = "username") String username, @CommandArgument(key = "*") String msg) {
      System.out.println(username + ": " + msg);

    }

    @SubCommand(format = "all *")
    public void msgAll(@CommandArgument(key = "*") String msg) {

    }
}
