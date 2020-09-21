package dev.nitrocommand.jda4.test;

import dev.nitrocommand.core.annotations.*;
import dev.nitrocommand.jda4.JDAController;
import dev.nitrocommand.jda4.annotations.JDAPermission;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

@NitroCommand(command = "kick", description = "Kicks a user", format = "WILL BE REMOBED")
@JDAPermission(Permission.KICK_MEMBERS)
public class KickCommand {
    @BaseCommand
    public void kickBase(TextChannel channel) {
        channel.sendMessage("Please enter 2 more arguments!").queue();
    }

    @SubCommand(format = "{user} *")
    public void kick(@CommandArgument("*") String reason, @CommandArgument("user") User user, TextChannel channel, Message message) {

        //channel.getGuild().kick(channel.getGuild().getMember(user), reason);
        //.queue();
        channel.sendMessage(user.getName() + " was kicked: " + reason).queue();
    }

    @SubCommand(format = "quiz *")
    public void kick(@CommandArgument("*") String user, TextChannel channel) {

        channel.sendMessage(user).queue();
    }

    @SubCommand(format = "random")
    public void anotherMethod(JDAController controller) {

    }
}
