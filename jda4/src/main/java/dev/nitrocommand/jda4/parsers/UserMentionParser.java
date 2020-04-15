package dev.nitrocommand.jda4.parsers;

import dev.nitrocommand.core.ArgumentParser;
import dev.nitrocommand.core.CommandCore;
import dev.nitrocommand.core.exceptions.ArgumentParserException;
import dev.nitrocommand.jda4.JDA4CommandCore;
import net.dv8tion.jda.api.entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMentionParser implements ArgumentParser<User> {
    private static final Pattern LONG_PATTERN = Pattern.compile("(<@!)(\\d+)(>)");
    private JDA4CommandCore commandCore;

    public UserMentionParser(CommandCore commandCore) {
        if (!(commandCore instanceof JDA4CommandCore)) {
            return;
        }
        this.commandCore = (JDA4CommandCore) commandCore;
    }

    @Override
    public User parse(String s) throws ArgumentParserException {
        Matcher matcher = LONG_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new ArgumentParserException("String does not match pattern "+ s);
        }

        return commandCore.getJDA().getUserById(matcher.group(2));
    }

    @Override
    public Class<?> getType() {
        return User.class;
    }
}
