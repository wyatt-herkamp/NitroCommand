package dev.nitrocommand.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation goes inside your method to tell it the name of the variable it is looking for.
 *
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandArgument {
    /**
     * The key for the format argument
     * This value you equal = * for the wild card value
     *
     * @return the key
     */
    String value();
}
