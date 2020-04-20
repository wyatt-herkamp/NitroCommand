package dev.nitrocommand.core.test;

import dev.nitrocommand.core.test.commands.CMD1;
import org.junit.jupiter.api.Test;

import static dev.nitrocommand.core.test.TestKeys.*;

public class TestMain {
    @Test
    public void testOne() {
        //Create Command Core
        TestCommandCore testCommandCore = new TestCommandCore();

        //Register Via package
        //testCommandCore.registerAllCommands("dev.nitrocommand.core.test.commands");

        testCommandCore.registerCommand(new CMD1());
        //Test Commands
        testCommandCore.execute("msg " + USERNAME + " " + MESSAGE);
        testCommandCore.execute("msg all " + MESSAGE_ALL);

        //TODO use JUnit Tests to check if it works easily
    }
}
