package dev.nitrocommand.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
