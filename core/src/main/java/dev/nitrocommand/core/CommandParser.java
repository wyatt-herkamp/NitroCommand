package dev.nitrocommand.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles are the parsing of commands.
 *
 * @since 1.0
 */
public class CommandParser {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{(.*)}");
    private static final String VARIABLE_KEY = "(.*[^ ])";

    public static String convertToRegex(String string) {
        String[] stringSplit = string.split(" ");
        for (int i = 0; i < stringSplit.length; i++) {
            if (!stringSplit[i].startsWith("{") && !stringSplit[i].equals("*")) {
                stringSplit[i] = "(" + stringSplit[i] + ")";
            }
        }
        string = StringUtils.join(stringSplit, " ");
        string = string.replaceAll("\\{(.*)}", VARIABLE_KEY);

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

        if (message.length() == 0 || message.split(" ").length == 0) return null;

        List<NitroSubCommand> possibilities = new ArrayList<>();
        for (NitroSubCommand sub : object.subCommands()) {
            for (String string : sub.formats()) {
                string = convertToRegex(string);
                if (message.matches(/*alias + " " + */string)) {
                    possibilities.add(sub);
                }
            }
        }

        if (possibilities.size() == 1) return possibilities.get(0);

        Map<Integer, NitroSubCommand> cascade = new HashMap<>();

        LevenshteinDistance l = new LevenshteinDistance();

        for (NitroSubCommand sub : possibilities) {
            for (String format : sub.formats()) {
                String newFormat = convertToRegex(/*firstFormat + " " + */format);
                Matcher matcher = Pattern.compile(newFormat).matcher(message);
                if (!matcher.matches()) continue;

                String newMessage = message;
                String[] split = newMessage.split(" ");
                if (format.endsWith("*")) {
                    String[] formatSplit = format.split(" ");
                    List<String> list = new ArrayList<>(Arrays.asList(formatSplit));
                    list.remove(formatSplit.length - 1);
                    List<String> messageSplit = new ArrayList<>(Arrays.asList(split));
                    messageSplit.subList(list.size(), messageSplit.size()).clear();
                    messageSplit.add("*");
                    newMessage = StringUtils.join(messageSplit, " ");
                }
                for (int i = 1; i < matcher.groupCount() - 1; i++) {
                    newMessage = newMessage.replace(matcher.group(i), "(.*[^ ])");
                }

                NitroCMD.LOGGER.debug("Message `" + message + "` " + "newMessage = " + newMessage + " Format " + newFormat);
                cascade.put(l.apply(newMessage, newFormat), sub);
            }
        }
        cascade.forEach((integer, nitroSubCommand) -> {
            System.out.println(integer + " " + nitroSubCommand.formats()[0]);
        });
        if (cascade.isEmpty()) return null;

        return cascade.get(Collections.min(cascade.keySet()));
    }

}
