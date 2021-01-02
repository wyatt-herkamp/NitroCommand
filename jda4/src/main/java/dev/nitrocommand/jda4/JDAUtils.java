package dev.nitrocommand.jda4;

import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.jda4.annotations.JDAPermission;
import net.dv8tion.jda.api.Permission;

public class JDAUtils {
    public static Permission getPermissionForSubCommand(NitroSubCommand subCommand) {
        if (subCommand.method().isAnnotationPresent(JDAPermission.class)) {
            return subCommand.method().getAnnotation(JDAPermission.class).value();
        } else if (subCommand.method().getDeclaringClass().isAnnotationPresent(JDAPermission.class)) {
            return subCommand.method().getDeclaringClass().getAnnotation(JDAPermission.class).value();
        }
        return Permission.MESSAGE_WRITE;
    }
}
