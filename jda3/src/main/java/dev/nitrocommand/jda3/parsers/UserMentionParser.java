package dev.nitrocommand.jda3.parsers;

import dev.nitrocommand.core.ArgumentParser;
import dev.nitrocommand.core.CommandCore;
import dev.nitrocommand.jda3.JDA3CommandCore;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMentionParser implements ArgumentParser<User> {
    private static final Pattern LONG_PATTERN = Pattern.compile("(<@)(\\d+)(>)");
    private JDA3CommandCore commandCore;

    public UserMentionParser(CommandCore commandCore) {
        if (!(commandCore instanceof JDA3CommandCore)) {
            return;
        }
        this.commandCore = (JDA3CommandCore) commandCore;
    }

    @Override
    public User parse(String s) {
        Matcher matcher = LONG_PATTERN.matcher(s);
        if (!matcher.matches()) {
            return null;
        }

        return commandCore.getJDA().getUserById(matcher.group(2));
    }

    @Override
    public Class<?> getType() {
        return User.class;
    }
}
