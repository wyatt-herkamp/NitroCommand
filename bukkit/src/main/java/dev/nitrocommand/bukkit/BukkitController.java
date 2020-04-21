package dev.nitrocommand.bukkit;

import org.bukkit.command.CommandSender;

public class BukkitController {
    private CommandSender commandSender;
    private String[] args;
    private String message;

    public BukkitController(CommandSender commandSender, String s, String[] args) {
        this.commandSender = commandSender;
        this.args = args;
        message = s;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public String[] getArgs() {
        return args;
    }

    public Object[] toArray() {
        return new Object[]{args, commandSender, message};
    }

    public String getMessage() {
        return message;
    }
}
