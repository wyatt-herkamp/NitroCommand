package dev.nitrocommand.jda3;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class JDAController {
    private GuildMessageReceivedEvent event;

    public JDAController(GuildMessageReceivedEvent message) {
        this.event = message;
    }

    public Member getAuthor() {
        return event.getMember();
    }

    public TextChannel getTextChannel() {
        return event.getMessage().getTextChannel();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    public GuildMessageReceivedEvent getEvent() {
        return event;
    }

    public Object[] toArray() {
        return new Object[]{event.getAuthor(), getTextChannel(), getGuild(), event, this};
    }
}
