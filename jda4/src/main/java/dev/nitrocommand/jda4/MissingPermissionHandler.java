package dev.nitrocommand.jda4;

import net.dv8tion.jda.api.Permission;

@FunctionalInterface
public interface MissingPermissionHandler {
    void handle(JDAController jdaController, Permission permission);
}
