package dev.nitrocommand.core;

public interface CommandCore {
    void registerCommand(Object object);
    /**
     * This will look for all Classes in a package to register commands in.
     * <b>We use the package to speed up the looking process</b>
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
    String getName();
}
