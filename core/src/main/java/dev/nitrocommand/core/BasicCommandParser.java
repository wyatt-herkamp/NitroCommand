package dev.nitrocommand.core;

import com.esotericsoftware.reflectasm.MethodAccess;
import dev.nitrocommand.core.annotations.CommandArgument;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicCommandParser implements CommandParser {
    private List<NitroCommandObject> commands = new ArrayList<>();

    public void add(NitroCommandObject nitroCommandObject) {
        commands.add(nitroCommandObject);
    }

    public static String convertToRegex(String string) {
        string = string.replaceAll("\\{(.*)}", "(.*[^ ])");

        if (string.contains("*") && string.endsWith("*")) string = string.replaceAll("\\*", "(.*)");

        return string;
    }

    //To test run TestMain#main


    @Override
    public NitroSubCommand locateCommand(String message) {
        NitroCMD.LOGGER.debug(String.format("Parsing: \"%s\".", message));
        NitroCommandObject object = getNitroCommandObject(message.split(" ")[0]);
        if (object == null) {
            return null;
        }
        List<NitroSubCommand> possibilities = new ArrayList<>();
        for (NitroSubCommand sub : object.subCommands()) {
            for (String alias : object.aliases()) {
                for (String string : sub.formats()) {
                    string = convertToRegex(string);

                    if (message.matches(alias + " " + string)) {
                        possibilities.add(sub);
                    }
                }
            }
        }

        if (possibilities.size() == 1) return possibilities.get(0);

        Map<Integer, NitroSubCommand> cascade = new HashMap<>();

        for (NitroSubCommand sub : possibilities) {
            for (String firstFormat : object.aliases()) {
                for (String format : sub.formats()) {
                    format = convertToRegex(firstFormat + " " + format);

                    Matcher matcher = Pattern.compile(format).matcher(message);
                    if (!matcher.matches()) continue;

                    String newMessage = message;

                    for (int i = 1; i < matcher.groupCount() - 1; i++) {
                        newMessage = newMessage.replaceAll(matcher.group(i), "(.*[^ ])");
                    }

                    cascade.put(new LevenshteinDistance().apply(newMessage, format), sub);
                }
            }
        }

        return cascade.get(Collections.min(cascade.keySet()));
    }

    @Override
    public void executeCommand(NitroSubCommand command, Object... paramSet) {
        //MethodAccess access = MethodAccess.get(command.command().value().getClass());
        ///  access.invoke(command.command().value(), command.methodName(), paramSet);
        try {
            command.method().invoke(command.command().value(), paramSet);
        } catch (IllegalAccessException e) {
            NitroCMD.LOGGER.error("Unable to access" + command.methodName(), e);
        } catch (InvocationTargetException e) {
            NitroCMD.LOGGER.error("Unable to invoke " + command.methodName(), e.getCause());
        }
    }

    @Override
    public boolean doesCommandExist(String command) {
        return getNitroCommandObject(command) != null;
    }

    public NitroCommandObject getNitroCommandObject(String command) {
        for (NitroCommandObject cmd : commands) {
            for (String s : cmd.aliases()) {
                if (s.equalsIgnoreCase(command)) return cmd;
            }
        }
        return null;
    }
}
