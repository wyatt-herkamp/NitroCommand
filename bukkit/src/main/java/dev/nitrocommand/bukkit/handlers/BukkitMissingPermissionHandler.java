package dev.nitrocommand.bukkit.handlers;

import dev.nitrocommand.bukkit.BukkitController;

@FunctionalInterface
public interface BukkitMissingPermissionHandler {
    void handle(BukkitController bukkitController, String permission);
}
