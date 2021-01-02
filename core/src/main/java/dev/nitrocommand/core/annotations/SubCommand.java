package dev.nitrocommand.core.annotations;

import java.lang.annotation.*;


@Repeatable(SubCommands.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * You can use multiple @SubCommand to make this method be used for multiple commands
 * @since 1.0
 */
public @interface SubCommand {
    /**
     * The format for this sub command.
     * Please read https://github.com/wherkamp/tuxcmd/wiki/understanding-command-format
     *
     * @return the subcommand format
     */
    String format();
}
