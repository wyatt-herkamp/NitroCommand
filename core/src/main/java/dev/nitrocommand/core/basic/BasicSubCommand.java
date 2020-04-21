package dev.nitrocommand.core.basic;

import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.annotations.BaseCommand;
import dev.nitrocommand.core.annotations.NitroPermission;
import dev.nitrocommand.core.annotations.SubCommand;
import dev.nitrocommand.core.annotations.SubCommands;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BasicSubCommand implements NitroSubCommand {
    private String[] formats;
    private Method method;
    private NitroCommandObject command;

    public BasicSubCommand(Method method, NitroCommandObject command) {
        this.method = method;
        this.command = command;
        if (method.getAnnotation(SubCommands.class) != null) {
            SubCommands subCommands = method.getAnnotation(SubCommands.class);
            formats = new String[subCommands.value().length];
            for (int i = 0; i < subCommands.value().length; i++) {
                formats[i] = subCommands.value()[i].format();
            }
        } else if (method.getAnnotation(SubCommand.class) != null) {
            formats = new String[1];
            formats[0] = method.getAnnotation(SubCommand.class).format();
        } else if (method.getAnnotation(BaseCommand.class) !=null) {

            formats = new String[1];
            formats[0] = "";
        }

    }

    @Override
    public String[] formats() {
        return formats;
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


    @Override
    public String toString() {
        return "BasicSubCommand{" +
                "formats=" + Arrays.toString(formats) +
                ", method=" + method.getName() +
                ", command=" + command.value().getClass().getSimpleName() +
                '}';
    }
}
