package dev.nitrocommand.jda3.parsers;

import dev.nitrocommand.core.ArgumentParser;
import dev.nitrocommand.core.CommandCore;
import dev.nitrocommand.core.exceptions.ArgumentParserException;
import dev.nitrocommand.jda3.JDA3CommandCore;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChannelMentionParser implements ArgumentParser<TextChannel> {
    private static final Pattern LONG_PATTERN = Pattern.compile("(<#)(\\d+)(>)");
    private JDA3CommandCore commandCore;

    public ChannelMentionParser(CommandCore commandCore) {
        if (!(commandCore instanceof JDA3CommandCore)) {
            return;
        }
        this.commandCore = (JDA3CommandCore) commandCore;
    }

    @Override
    public TextChannel parse(String s) throws ArgumentParserException{
        Matcher matcher = LONG_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new ArgumentParserException("String does not match pattern");
        }

        return commandCore.getJDA().getTextChannelById(matcher.group(2));
    }

    @Override
    public Class<?> getType() {
        return TextChannel.class;
    }
}
