package dev.nitrocommand.jda4;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

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
        return new Object[]{event.getAuthor(), getTextChannel(), getGuild(), event, this, getMessage()};
    }
}
