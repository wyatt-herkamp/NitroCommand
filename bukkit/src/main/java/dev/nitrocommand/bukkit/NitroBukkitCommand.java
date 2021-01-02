package dev.nitrocommand.bukkit;

import dev.nitrocommand.bukkit.annotations.BukkitPermission;
import dev.nitrocommand.core.CommandParser;
import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.Utils;
import me.kingtux.simpleannotation.MethodFinder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.lang.reflect.Method;
import java.util.List;

public class NitroBukkitCommand extends Command {

    private final BukkitCommandCore core;
    private final NitroCommandObject object;

    protected NitroBukkitCommand(String name, String description, String usageMessage, List<String> aliases, BukkitCommandCore core, NitroCommandObject commandObject) {
        super(name, description, usageMessage, aliases);
        this.core = core;
        this.object = commandObject;
        registerPermissions();
    }

    private void registerPermissions() {
        if (getClass().isAnnotationPresent(BukkitPermission.class)) {
            BukkitPermission annotation = getClass().getAnnotation(BukkitPermission.class);
            if (annotation.register())
                Bukkit.getPluginManager().addPermission(new Permission(annotation.value(), annotation.description()));
        }
        for (Method method : MethodFinder.getAllMethodsWithAnnotation(getClass(), BukkitPermission.class)) {
            BukkitPermission annotation = method.getAnnotation(BukkitPermission.class);
            if (annotation.register())
                Bukkit.getPluginManager().addPermission(new Permission(annotation.value(), annotation.description()));
        }
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender == null) {
            System.out.println("WTF");
        }
        String message = String.join(" ", strings);
        NitroSubCommand subCommand = (strings.length == 0) ? object.getBaseExecutor() : CommandParser.locateSubCommand(message, object);

        if (subCommand == null) {
            subCommand = object.getBaseExecutor();
            return false;
        }
        BukkitController controller = new BukkitController(commandSender, s, strings);
        String permission = BukkitUtils.getPermissionForSubCommand(subCommand);

        if (!permission.isEmpty()) {
            if (!commandSender.hasPermission(permission)) {
                core.getMissingPermissionHandler().handle(controller, permission);
                return false;
            }
        }


        for (Class<?> type : subCommand.method().getParameterTypes()) {
            if (type.isAssignableFrom(commandSender.getClass())) {
                if (commandSender instanceof Player && !type.isAssignableFrom(Player.class)) {
                    core.getMustBeAPlayerHandler().handle(controller);
                    return false;
                }
            }
        }
        Utils.executeCommand(subCommand, Utils.getArguments(message, subCommand, subCommand.method().getParameters(), controller.toArray(), core));
        return true;
    }
}
