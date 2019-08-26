package dev.nitrocommand.jda4.test;

import dev.nitrocommand.core.annotations.*;
import dev.nitrocommand.jda4.JDAController;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

@NitroCommand(command = "kick", description = "Kicks a user", format = "WILL BE REMOBED")
public class KickCommand {
    @BaseCommand
    public void kickBase(TextChannel channel) {
        channel.sendMessage("Please enter 2 more arguments!").queue();
    }

    @NitroPermission(permission = "KICK_MEMBERS")
    @SubCommand(format = "{user} *")
    public void kick(@CommandArgument("*") String reason, @CommandArgument("user") User user, TextChannel channel, Message message) {
        channel.getGuild().kick(channel.getGuild().getMember(user), reason);
        //.queue();
        channel.sendMessage(user.getName() + " was kicked: " + reason).queue();
    }

    @SubCommand(format = "random")
    public void anotherMethod(JDAController controller) {

    }
}
