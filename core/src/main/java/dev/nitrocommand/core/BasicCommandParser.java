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
    public static Object[] getArguments(String message, NitroSubCommand command, Parameter[] parameters, Object[] otherArguments) {
        final Object[] arguments = new Object[parameters.length];

        for (String format : command.formats()) {
            for (int i = 0; i < parameters.length; i++) {
                CommandArgument argument = parameters[i].getAnnotation(CommandArgument.class);
                if (parameters[i].getType() == String.class) {
                    if (argument != null) {
                        arguments[i] = parseVariableValue(argument.key(), message, format);
                    }else {
                        arguments[i] = message;
                    }
                }  else if (parameters[i].getType() == String[].class) {
                    arguments[i] = message.split(" ");
                } else {
                    if (argument != null) {

                    } else {
                        arguments[i] = getByType(otherArguments, parameters[i].getType());
                    }
                }
            }

            return arguments;
        }

        return arguments;
    }

    public static String parseVariableValue(String key, String message, String format) {
        if (key.equals("*")) {
            return getWildCardValue(message, format);
        } else {
            return getVariableValue(key, message, format);
        }
    }

    public static String getWildCardValue(String message, String format) {
        //Parse for wildcard
        String[] formatSplit = format.split(" ");
        int d = 0;
        for (int j = 0; j < formatSplit.length; j++) {
            if (formatSplit[j].equals("*")) {
                d = j + 1;
                break;
            }
        }
        String[] messageSplit = message.split(" ");
        String[] stringTwo = Arrays.copyOfRange(messageSplit, d, messageSplit.length);
        return String.join(" ", stringTwo);
    }

    public static String getVariableValue(String key, String message, String format) {
        int count = 0;
        int num = 0;

        for (int d = 0; d < format.length(); d++) {
            if (format.charAt(d) == '{') {
                String formatCount = format.substring(d);

                count++;

                if (formatCount.startsWith("{" + key + "}"))
                    num = count;
            }
        }

        return message.split(" ")[num];
    }

    /**
     * This takes an array and finds witch one matches a type.
     *
     * @param args The arguments being looked through.
     * @param type Which type.
     * @return The correct argument.
     */
    public static Object getByType(Object[] args, Class<?> type) {
        for (Object o : args) {
            if (o.getClass() == type) {
                return o;
            } else if (type.isInstance(o)) {
                return o;
            }
        }
        return null;
    }

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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
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
