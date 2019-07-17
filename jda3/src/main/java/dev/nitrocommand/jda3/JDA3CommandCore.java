package dev.nitrocommand.jda3;

import dev.nitrocommand.core.BasicCommandParser;
import dev.nitrocommand.core.basic.BasicCommandCore;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

public class JDA3CommandCore extends BasicCommandCore implements EventListener {
    private JDA jda;
    private String prefix = "/";
    private Map<Long, String> customPrefixes = new HashMap<>();
    private BasicCommandParser parser = new BasicCommandParser();

    public JDA3CommandCore(JDA jda, String prefix) {
        this.jda = jda;
        this.prefix = prefix;
        jda.addEventListener(this);
    }

    @Override
    public String getName() {
        return "JDA3";
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent messageEvent = (GuildMessageReceivedEvent) event;
            if (isCommand(messageEvent.getMessage().getContentRaw())) {
                String stripContent = stripCommand(messageEvent.getMessage().getContentRaw());
                executeCommand(stripContent, messageEvent, messageEvent.getChannel(), messageEvent.getAuthor(), messageEvent.getMessage(), messageEvent.getMember(), messageEvent.getGuild(), new JDAController(messageEvent.getMessage()));
            }
            //TODO run event
        }
    }

    private String stripCommand(String contentRaw) {
        if (contentRaw.startsWith(prefix)) return contentRaw.substring(prefix.length());
        for (Map.Entry<Long, String> entry : customPrefixes.entrySet()) {
            String s = entry.getValue();
            if (contentRaw.startsWith(s)) return contentRaw.substring(s.length());
        }
        return contentRaw;
    }

    private boolean isCommand(String contentRaw) {
        if (contentRaw.startsWith(prefix)) {
            return true;
        }
        for (Map.Entry<Long, String> entry : customPrefixes.entrySet()) {
            Long aLong = entry.getKey();
            String s = entry.getValue();
            if (contentRaw.startsWith(s)) continue;
        }
        return false;
    }

    public JDA getJDA() {
        return jda;
    }
}
