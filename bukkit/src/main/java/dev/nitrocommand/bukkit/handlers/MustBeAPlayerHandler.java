package dev.nitrocommand.bukkit.handlers;

import dev.nitrocommand.bukkit.BukkitController;

@FunctionalInterface
public interface MustBeAPlayerHandler {
    void handle(BukkitController bukkitController);
}
