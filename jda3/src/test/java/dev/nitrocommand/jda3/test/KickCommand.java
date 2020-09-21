package dev.nitrocommand.jda3.test;

import dev.nitrocommand.core.annotations.*;
import dev.nitrocommand.jda3.JDAController;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

@NitroCommand(command = "kick", description = "Kicks a user", format = "WILL BE REMOBED")
public class KickCommand {
    @BaseCommand
    public void kickBase(TextChannel channel) {
        channel.sendMessage("Please enter 2 more arguments!").queue();
    }

    @SubCommand(format = "{user} *")
    public void kick(@CommandArgument("*") String reason, @CommandArgument("user") User user, TextChannel channel, Message message) {
        //channel.getGuild().getController().kick(channel.getGuild().getMember(user), reason);
        //.queue();
        channel.sendMessage(user.getName() + " was kicked: " + reason).queue();
    }

    @SubCommand(format = "random")
    public void anotherMethod(JDAController controller) {

    }
}
