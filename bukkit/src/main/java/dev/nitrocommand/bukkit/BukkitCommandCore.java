package dev.nitrocommand.bukkit;

import com.google.common.collect.Lists;
import dev.nitrocommand.bukkit.handlers.BukkitMissingPermissionHandler;
import dev.nitrocommand.bukkit.handlers.MustBeAPlayerHandler;
import dev.nitrocommand.core.NitroCMD;
import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.annotations.NitroCommand;
import dev.nitrocommand.core.basic.BasicCommandCore;
import dev.nitrocommand.core.basic.BasicCommandObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BukkitCommandCore extends BasicCommandCore<CommandSender> {
    private Plugin plugin;
    private BukkitMissingPermissionHandler missingPermissionHandler;
    private MustBeAPlayerHandler mustBeAPlayerHandler;

    public BukkitCommandCore(Plugin plugin) {
        this.plugin = plugin;
        if (NitroCMD.LOGGER == null || NitroCMD.LOGGER.getName().equals("NOP")) {
            System.out.println("Overriding NOP with Bukkit Logger");
            NitroCMD.LOGGER = new BukkitBasedLogger(plugin);
        }
        missingPermissionHandler = (bukkitController, permission) -> {
            bukkitController.getCommandSender().sendMessage("Missing Permission: " + permission);
        };
        mustBeAPlayerHandler = bukkitController -> bukkitController.getCommandSender().sendMessage("Must be a player");
    }

    @Override
    public NitroCommandObject registerCommand(Object object) {
        NitroCommandObject commandObject = super.registerCommand(object);
        List<String> aliases = new ArrayList<>(Arrays.asList(commandObject.aliases()));
        aliases.remove(0);
        Command command = new NitroBukkitCommand(commandObject.aliases()[0], commandObject.description(), commandObject.format(), aliases, this, commandObject);
        getCommandMap().register(plugin.getName().toLowerCase(), command);
        return commandObject;
    }

    private CommandMap getCommandMap() {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            return (CommandMap) commandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            NitroCMD.LOGGER.error("Failure to get CommandMap", e);
            return null;
        }
    }

    @Override
    public String getName() {
        return "bukkit";
    }

    @Override
    public void sendMessage(CommandSender senderObject, String message) {
        senderObject.sendMessage(message);
    }

    public BukkitMissingPermissionHandler getMissingPermissionHandler() {
        return missingPermissionHandler;
    }

    public void setMissingPermissionHandler(BukkitMissingPermissionHandler missingPermissionHandler) {
        if (missingPermissionHandler == null) {
            throw new IllegalArgumentException("Can not be null");
        }
        this.missingPermissionHandler = missingPermissionHandler;
    }

    public MustBeAPlayerHandler getMustBeAPlayerHandler() {
        return mustBeAPlayerHandler;
    }

    public void setMustBeAPlayerHandler(MustBeAPlayerHandler mustBeAPlayerHandler) {
        if (mustBeAPlayerHandler == null) {
            throw new IllegalArgumentException("Can not be null");
        }
        this.mustBeAPlayerHandler = mustBeAPlayerHandler;
    }
}
