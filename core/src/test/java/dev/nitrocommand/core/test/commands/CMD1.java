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
    public void msg(@CommandArgument("username") String username, @CommandArgument("*") String msg) throws Exception {
        System.out.println(username + ": " + msg);

    }

    @SubCommand(format = "all *")
    public void msgAll(@CommandArgument("*") String msg) throws Exception {

    }

    @SubCommand(format = "bobby")
    public void bobby() {
        System.out.println("Bobby");
    }
}
