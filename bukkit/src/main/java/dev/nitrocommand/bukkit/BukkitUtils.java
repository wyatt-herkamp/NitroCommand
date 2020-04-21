package dev.nitrocommand.bukkit;

import dev.nitrocommand.bukkit.annotations.BukkitPermission;
import dev.nitrocommand.core.NitroSubCommand;

public class BukkitUtils {
    public static String getPermissionForSubCommand(NitroSubCommand subCommand) {
        if (subCommand.method().isAnnotationPresent(BukkitPermission.class)) {
            return subCommand.method().getAnnotation(BukkitPermission.class).value();
        } else if (subCommand.method().getDeclaringClass().isAnnotationPresent(BukkitPermission.class)) {
            return subCommand.method().getDeclaringClass().getAnnotation(BukkitPermission.class).value();
        }
        return "";
    }
}
