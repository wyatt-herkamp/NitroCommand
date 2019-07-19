package dev.nitrocommand.bukkit;

import dev.nitrocommand.core.CommandParser;
import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NitroBukkitCommand extends Command {
    private BukkitCommandCore core;
    private NitroCommandObject object;

    protected NitroBukkitCommand(String name, String description, String usageMessage, List<String> aliases, BukkitCommandCore core, NitroCommandObject commandObject) {
        super(name, description, usageMessage, aliases);
        this.core = core;
        this.object = commandObject;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        String message = String.join(" ", strings);

        NitroSubCommand subCommand;
        if (strings.length == 0) {
            subCommand = object.getBaseExecutor();

        } else {
            subCommand = CommandParser.locateSubCommand(message, object);
        }
        if (subCommand == null) {
            subCommand = object.getBaseExecutor();
            //TODO base command
        }
        for (Class<?> type : subCommand.method().getParameterTypes()) {
            if (type.isAssignableFrom(commandSender.getClass())) {
                if (commandSender instanceof Player && !type.isAssignableFrom(Player.class)) {
                    core.sendMessage(commandSender, "You must be a player for this to work");
                    return true;
                }
            }
        }
        Utils.executeCommand(subCommand, Utils.getArguments(message, subCommand, subCommand.method().getParameters(), new CommandSender[]{commandSender}, core));
        return true;
    }
}
