package dev.nitrocommand.jda4;

import net.dv8tion.jda.api.Permission;

@FunctionalInterface
public interface MissingPermissionHandler {
    void handle(String message, String newMessage, JDAController jdaController, Permission permission);
}
