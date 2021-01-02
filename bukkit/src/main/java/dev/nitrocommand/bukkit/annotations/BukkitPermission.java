package dev.nitrocommand.bukkit.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for adding permissions to your command.
 *
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BukkitPermission {
    /**
     * The permission value.
     * This value is required.
     *
     * @return the permission value.
     */
    String value();

    /**
     * If you want to add a description to the permission do that here. NitroCommand auto registers them
     *
     * @return the description.
     */
    String description() default "";

    /**
     * Whether or not you want NitroCommand to register the permission.
     *
     * @return do you want NitroCommand to register the permission.
     */
    boolean register() default true;
}
