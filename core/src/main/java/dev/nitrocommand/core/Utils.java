package dev.nitrocommand.core;

import dev.nitrocommand.core.annotations.CommandArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * These are internal utils for NitroCommand
 * Probably shouldnt use unless you know what you are doing
 */
public class Utils {
    public static Object[] getArguments(String message, NitroSubCommand command, Parameter[] parameters, Object[] otherArguments, CommandCore commandCore) {
        final Object[] arguments = new Object[parameters.length];
        String format = command.formats()[0];
        for (int i = 0; i < parameters.length; i++) {
            CommandArgument argument = parameters[i].getAnnotation(CommandArgument.class);
            if (parameters[i].getType() == String.class) {
                if (argument != null) {
                    arguments[i] = parseVariableValue(argument.value(), message, format);
                } else {
                    arguments[i] = message;
                }
            } else if (parameters[i].getType() == String[].class) {
                arguments[i] = message.split(" ");
            } else {
                if (argument != null) {
                    ArgumentParser parser = commandCore.getArgumentParser(parameters[i].getType());
                    arguments[i] = parser.parse(parseVariableValue(argument.value(), message, format));
                } else {
                    arguments[i] = getByType(otherArguments, parameters[i].getType());
                }
            }
        }


        return arguments;
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

    public static boolean contructorExists(Class<?> t, Class<?>... clazzes) {
        try {
            t.getConstructor(clazzes);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    public static void executeCommand(NitroSubCommand command, Object... paramSet) {
        try {
            command.method().invoke(command.command().value(), paramSet);
        } catch (IllegalAccessException e) {
            NitroCMD.LOGGER.error("Unable to access" + command.methodName(), e);
        } catch (InvocationTargetException e) {
            NitroCMD.LOGGER.error("Unable to invoke " + command.methodName(), e.getCause());
        }
    }
}
