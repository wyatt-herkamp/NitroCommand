package dev.nitrocommand.jda3;

import net.dv8tion.jda.core.entities.*;

public class JDAController {
    private Message message;

    public JDAController(Message message) {
        this.message = message;
    }

    public Member getAuthor() {
        return message.getMember();
    }

    public TextChannel getTextChannel() {
        return message.getTextChannel();
    }

    public Guild getGuild() {
        return message.getGuild();
    }

    public Message getMessage() {
        return message;
    }
}
