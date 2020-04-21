package dev.nitrocommand.bukkit.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface BukkitPermission {
    String value();
}
