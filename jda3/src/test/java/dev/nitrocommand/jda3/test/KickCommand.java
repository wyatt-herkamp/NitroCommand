package dev.nitrocommand.jda3.test;

import dev.nitrocommand.core.annotations.BaseCommand;
import dev.nitrocommand.core.annotations.CommandArgument;
import dev.nitrocommand.core.annotations.NitroCommand;
import dev.nitrocommand.core.annotations.SubCommand;
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
        channel.getGuild().getController().kick(channel.getGuild().getMember(user), reason);
        //.queue();
        channel.sendMessage(user.getName() + " was kicked: " + reason).queue();
    }
}
