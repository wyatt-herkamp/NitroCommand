package dev.nitrocommand.core.test;

import dev.nitrocommand.core.test.commands.CMD1;
import org.junit.jupiter.api.Test;

public class TestMain {
    @Test
    public void testOne() {
        //Create Command Core
        TestCommandCore testCommandCore = new TestCommandCore();

        //Register Via package
        //testCommandCore.registerAllCommands("dev.nitrocommand.core.test.commands");

        testCommandCore.registerCommand(new CMD1());
        //Test Commands
        testCommandCore.execute("msg bobby How are you doing?");

        //TODO use JUnit Tests to check if it works easily
    }
}
