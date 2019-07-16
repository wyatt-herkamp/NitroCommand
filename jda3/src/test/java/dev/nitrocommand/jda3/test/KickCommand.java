package dev.nitrocommand.jda3.test;

import dev.nitrocommand.core.annotations.BaseCommand;
import dev.nitrocommand.core.annotations.CommandArgument;
import dev.nitrocommand.core.annotations.NitroCommand;
import dev.nitrocommand.core.annotations.SubCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

@NitroCommand(command = "kick", description = "Kicks a user", format = "WILL BE REMOBED")
public class KickCommand {
    @BaseCommand
    public void kickBase(TextChannel channel){
        channel.sendMessage("Please enter 2 more arguments!").queue();
    }

    @SubCommand(format = "{user} *")
    public void kick(@CommandArgument(key = "*") String reason, TextChannel channel, Message message){
        channel.getGuild().getController().kick(message.getMentionedMembers().get(0), reason).queue();
        channel.sendMessage("User was kicked: " + reason).queue();
    }
}
