package dev.nitrocommand.core;

import java.util.List;

public interface CommandCore<T> {
    NitroCommandObject registerCommand(Object object);

    /**
     * This will look for all Classes in a package to register commands in.
     * <b>We use the package to speed up the looking process</b>
     *
     * @param packageToLookIn package to look in
     */
    void registerAllCommands(String packageToLookIn);

    /**
     * This will look for all Classes in a package to register commands in.
     *
     * @param packageToLookIn package to look in
     * @param value           An argument all of them need to work. such as a Bukkit Plugin or your Bots main class (Warning the all must have the same requirements)
     */
    void registerAllCommands(String packageToLookIn, Object... value);

    ArgumentParser getArgumentParser(Class<?> type);

    void registerArgumentParser(ArgumentParser parser);

    String getName();


    List<NitroCommandObject> registeredCommands();

    boolean doesCommandExist(String command);

    NitroCommandObject getCommand(String command);

    void sendMessage(T senderObject, String message);
}
