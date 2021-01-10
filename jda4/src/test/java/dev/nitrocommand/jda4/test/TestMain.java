package dev.nitrocommand.jda4.test;



import dev.nitrocommand.jda4.JDA4CommandCore;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {
    public static void main(String[] args) throws IOException, LoginException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(System.getProperty("user.home"), "discord-test.properties")));
        JDA jda = JDABuilder.createDefault(properties.getProperty("token")).setActivity(Activity.playing("Testing NitroCommand")).build();
        JDA4CommandCore core = new JDA4CommandCore(jda, "/");

        core.registerCommand(new KickCommand());
    }
}
