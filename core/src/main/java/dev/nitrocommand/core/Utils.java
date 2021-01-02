package dev.nitrocommand.core;

import dev.nitrocommand.core.annotations.CommandArgument;
import dev.nitrocommand.core.exceptions.ArgumentParserException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * These are internal utils for NitroCommand
 * Probably shouldnt use unless you know what you are doing
 */
public class Utils {
    public static Object[] getArguments(String message, NitroSubCommand command, Parameter[] parameters, Object[] otherArguments, CommandCore commandCore) {
        return getArguments(message, message, command, parameters, otherArguments, commandCore);
    }

    public static Object[] getArguments(String message, String fullMessage, NitroSubCommand command, Parameter[] parameters, Object[] otherArguments, CommandCore commandCore) {
        final Object[] arguments = new Object[parameters.length];
        String format = command.formats()[0];
        for (int i = 0; i < parameters.length; i++) {
            CommandArgument argument = parameters[i].getAnnotation(CommandArgument.class);
            if (parameters[i].getType() == String.class) {
                if (argument != null) {
                    arguments[i] = parseVariableValue(argument.value(), message, format);
                } else {
                    arguments[i] = fullMessage;
                }
            } else if (parameters[i].getType() == String[].class) {
                arguments[i] = message.split(" ");
            } else {
                if (argument != null) {
                    ArgumentParser parser = commandCore.getArgumentParser(parameters[i].getType());
                    if (parser == null) {
                        arguments[i] = null;
                        try {
                            throw new IllegalArgumentException("No argument parser for this type");
                        } catch (IllegalArgumentException e) {
                            NitroCMD.LOGGER.error("Unable to get value for argument", e);
                        }
                    }
                    try {
                        arguments[i] = parser.parse(parseVariableValue(argument.value(), message, format));
                    } catch (ArgumentParserException e) {
                        NitroCMD.LOGGER.error("Unable to parse Argument", e);
                        return null;
                    }
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
        String[] params = message.split(" ");
        String[] formatSplit = format.split(" ");
        for (int i = 0; i < formatSplit.length; i++) {
            if (formatSplit[i].equals("*")) {
                StringBuilder builder = new StringBuilder();
                for (int i1 = i; i1 < params.length; i1++) {
                    builder.append(params[i1]);
                    if (i1 != params.length - 1)
                        builder.append(" ");
                }
                return builder.toString();
            }
        }
        return null;
    }

    public static String getVariableValue(String key, String message, String format) {

        String[] params = message.split(" ");
        String[] formatSplit = format.split(" ");
        for (int i = 0; i < formatSplit.length; i++) {
            if (formatSplit[i].equals("{" + key + "}")) {
                return params[i];
            }
        }
        return null;
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
        Arrays.stream(paramSet).forEach(o -> {
            NitroCMD.LOGGER.debug(o.getClass().getSimpleName());
        });
        try {
            command.method().invoke(command.command().value(), paramSet);
        } catch (IllegalAccessException e) {
            NitroCMD.LOGGER.error("Unable to access" + command.methodName(), e);
        } catch (InvocationTargetException e) {
            NitroCMD.LOGGER.error("Unable to invoke " + command.methodName(), e.getCause());
        }
    }

    public static List<String> executeTabCompletion(NitroTabCompleter tabCompleter, NitroCommandObject object, Object... params) {
        Arrays.stream(params).forEach(o -> {
            NitroCMD.LOGGER.debug(o.getClass().getSimpleName());
        });
        try {
            tabCompleter.method().invoke(tabCompleter.command().value(), params);
        } catch (IllegalAccessException e) {
            NitroCMD.LOGGER.error("Unable to access" + tabCompleter.methodName(), e);
        } catch (InvocationTargetException e) {
            NitroCMD.LOGGER.error("Unable to invoke " + tabCompleter.methodName(), e.getCause());
        }
        return Collections.emptyList();
    }
}
