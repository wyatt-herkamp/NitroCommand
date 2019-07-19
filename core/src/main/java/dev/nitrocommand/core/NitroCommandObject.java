package dev.nitrocommand.core;

import java.util.List;

/**
 * This represents a NitroCommand
 */
public interface NitroCommandObject extends Iterable<NitroSubCommand> {
    /**
     * A list of NitroSubCommands
     *
     * @return all sub commands for this command
     */
    List<NitroSubCommand> subCommands();

    /**
     * All aliases for this sub command
     *
     * @return aliases
     */
    String[] aliases();

    /**
     * The Description for this command
     *
     * @return the description
     */
    String description();

    NitroSubCommand getBaseExecutor();

    Object value();

    String getPermission();

    String format();
}
