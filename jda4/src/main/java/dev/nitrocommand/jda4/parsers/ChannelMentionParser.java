package dev.nitrocommand.jda4.parsers;

import dev.nitrocommand.core.ArgumentParser;
import dev.nitrocommand.core.CommandCore;
import dev.nitrocommand.core.exceptions.ArgumentParserException;
import dev.nitrocommand.jda4.JDA4CommandCore;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChannelMentionParser implements ArgumentParser<TextChannel> {
    private static final Pattern LONG_PATTERN = Pattern.compile("(<#)(\\d+)(>)");
    private JDA4CommandCore commandCore;

    public ChannelMentionParser(CommandCore commandCore) {
        if (!(commandCore instanceof JDA4CommandCore)) {
            return;
        }
        this.commandCore = (JDA4CommandCore) commandCore;
    }

    @Override
    public TextChannel parse(String s) throws ArgumentParserException {
        Matcher matcher = LONG_PATTERN.matcher(s);
        if (!matcher.matches()) {
            return null;
        }

        return commandCore.getJDA().getTextChannelById(matcher.group(2));
    }

    @Override
    public Class<?> getType() {
        return TextChannel.class;
    }
}
