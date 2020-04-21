package dev.nitrocommand.core.basic;

import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.NitroTabCompleter;

import java.lang.reflect.Method;
import java.util.List;

public class BasicTabCompleter implements NitroTabCompleter {
    private Method method;
    private NitroCommandObject command;
    private String variable;

    public BasicTabCompleter(Method method, NitroCommandObject command) {
        this.method = method;
        this.command = command;
    }

    @Override
    public String variable() {
        return variable;
    }

    @Override
    public String methodName() {
        return method.getName();
    }

    @Override
    public Method method() {
        return method;
    }

    @Override
    public NitroCommandObject command() {
        return command;
    }
}
