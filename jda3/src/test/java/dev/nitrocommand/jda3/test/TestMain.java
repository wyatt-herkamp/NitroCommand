package dev.nitrocommand.jda3.test;

import dev.nitrocommand.core.NitroCMD;
import dev.nitrocommand.jda3.JDA3CommandCore;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestMain {
    public static void main(String[] args) throws IOException, LoginException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(System.getProperty("user.home"), "discord-test.properties")));
        JDA jda = new JDABuilder(properties.getProperty("token")).setGame(Game.of(Game.GameType.DEFAULT, "Testing NitroCommand")).build();
        JDA3CommandCore core = new JDA3CommandCore(jda, "/");

        core.registerCommand(new KickCommand());
    }
}
