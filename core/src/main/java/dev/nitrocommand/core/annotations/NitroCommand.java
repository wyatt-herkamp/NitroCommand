package dev.nitrocommand.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The parent annotation for your Command.
 *
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NitroCommand {
    /**
     * The commands that this object will handle.
     *
     * @return the commands that this object will handle.
     */
    String[] command();

    String description();

    String format();
}
