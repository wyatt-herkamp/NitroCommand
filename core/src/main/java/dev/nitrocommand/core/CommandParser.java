package dev.nitrocommand.core;

/**
 * Subject to rename
 */
public interface CommandParser {
    NitroSubCommand locateCommand(String message);
    void executeCommand(NitroSubCommand command, Object... paramSet);
    boolean doesCommandExist(String command);
}
