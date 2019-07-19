package dev.nitrocommand.core;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Subject to rename
 */
public class CommandParser {
    public static String convertToRegex(String string) {
        string = string.replaceAll("\\{(.*)}", "(.*[^ ])");

        if (string.contains("*") && string.endsWith("*")) string = string.replaceAll("\\*", "(.*)");

        return string;
    }

    /**
     * This uses our advance high tech code by Mono to locate the sub command
     *
     * @param message the message object With out the / or the base command aliase
     * @param object  the command object
     * @return the SubCommand if found
     */
    public static NitroSubCommand locateSubCommand(String message, NitroCommandObject object) {
        NitroCMD.LOGGER.debug(String.format("Parsing: \"%s\".", message));

        if (message.length()==0||message.split(" ").length == 0 ) return null;

        List<NitroSubCommand> possibilities = new ArrayList<>();
        for (NitroSubCommand sub : object.subCommands()) {
            //for (String alias : object.aliases()) {
                for (String string : sub.formats()) {
                    string = convertToRegex(string);

                    if (message.matches(/*alias + " " + */string)) {
                        possibilities.add(sub);
                    }
                }
            //}
        }

        if (possibilities.size() == 1) return possibilities.get(0);

        Map<Integer, NitroSubCommand> cascade = new HashMap<>();

        for (NitroSubCommand sub : possibilities) {
            //for (String firstFormat : object.aliases()) {
                for (String format : sub.formats()) {
                    format = convertToRegex(/*firstFormat + " " + */format);

                    Matcher matcher = Pattern.compile(format).matcher(message);
                    if (!matcher.matches()) continue;

                    String newMessage = message;

                    for (int i = 1; i < matcher.groupCount() - 1; i++) {
                        newMessage = newMessage.replaceAll(matcher.group(i), "(.*[^ ])");
                    }

                    cascade.put(new LevenshteinDistance().apply(newMessage, format), sub);
                }
            //}
        }
        if (cascade.isEmpty()) return null;
        return cascade.get(Collections.min(cascade.keySet()));
    }

}
