package dev.nitrocommand.core.basic;

import dev.nitrocommand.core.NitroCommandObject;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.annotations.NitroPermission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BasicCommandObject implements NitroCommandObject {
    private List<NitroSubCommand> subCommands;
    private String[] aliases;
    private String description, format;
    private NitroSubCommand baseCommand;
    private Object value;
    private String permission = "";

    public BasicCommandObject(List<NitroSubCommand> subCommands, String[] aliases, String format, NitroSubCommand baseCommand, Object value) {
        this.subCommands = subCommands;
        this.aliases = aliases;
        this.format = format;
        this.baseCommand = baseCommand;
        this.value = value;
        if (value.getClass().getAnnotation(NitroPermission.class) != null) {
            permission = value.getClass().getAnnotation(NitroPermission.class).permission();
        }
    }

    public BasicCommandObject(String[] aliases, NitroSubCommand baseCommand, String format, Object value) {
        this(new ArrayList<>(), aliases, format, baseCommand, value);
    }

    @Override
    public List<NitroSubCommand> subCommands() {
        return subCommands;
    }

    @Override
    public String[] aliases() {
        return aliases;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public NitroSubCommand getBaseExecutor() {
        return baseCommand;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String format() {
        return format;
    }

    @Override
    public Iterator<NitroSubCommand> iterator() {
        return subCommands.iterator();
    }

    public boolean add(NitroSubCommand command) {
        return subCommands.add(command);
    }

    //For Later
    public List<NitroSubCommand> getSubCommands() {
        return subCommands;
    }

    public void setSubCommands(List<NitroSubCommand> subCommands) {
        this.subCommands = subCommands;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public NitroSubCommand getBaseCommand() {
        return baseCommand;
    }

    public void setBaseCommand(NitroSubCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BasicCommandObject{" +
                "subCommands=" + subCommands.size() +
                ", aliases=" + Arrays.toString(aliases) +
                ", description='" + description + '\'' +
                ", baseCommand=" + baseCommand +
                ", value=" + value +
                ", permission" + permission +
                '}';
    }
}
