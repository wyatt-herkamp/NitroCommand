package dev.nitrocommand.jda4;

import dev.nitrocommand.core.*;
import dev.nitrocommand.core.basic.BasicCommandCore;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JDA4CommandCore extends BasicCommandCore<TextChannel> implements EventListener {
    private JDA jda;
    private String prefix = "/";
    private Map<Long, String> customPrefixes = new HashMap<>();

    public JDA4CommandCore(JDA jda, String prefix) {
        this.jda = jda;
        this.prefix = prefix;
        jda.addEventListener(this);
    }

    @Override
    public String getName() {
        return "JDA3";
    }


    @Override
    public void sendMessage(TextChannel senderObject, String message) {
        senderObject.sendMessage(message).queue();
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent messageEvent = (GuildMessageReceivedEvent) event;
            if (isCommand(messageEvent.getMessage().getContentRaw())) {
                String stripContent = stripCommand(messageEvent.getMessage().getContentRaw());
                executeCommand(stripContent, new JDAController(messageEvent));
            }
        }
    }

    private void executeCommand(String message, JDAController controller) {
        String commandBase = message.split(" ")[0];
        if (!doesCommandExist(commandBase)) {
            NitroCMD.LOGGER.debug("No base command found: " + message);
            return;
        }
        NitroCommandObject object = getCommand(commandBase);
        NitroSubCommand command;
        String newMessage = stripCommand(message);

        if(newMessage.length() == commandBase.length()){
            command = object.getBaseExecutor();
        }else {
            newMessage = newMessage.substring(commandBase.length() + 1);
            command = CommandParser.locateSubCommand(newMessage, object);
        }
        if (command == null) {
            command = object.getBaseExecutor();
        }
        if (!command.requiredPermission().isEmpty()) {
            NitroSubCommand finalCommand = command;
            Permission permission = Arrays.stream(Permission.values()).filter(p -> p.name().equalsIgnoreCase(finalCommand.requiredPermission())).findFirst().orElse(Permission.MESSAGE_WRITE);
            if (!controller.getAuthor().hasPermission(permission)) {
                controller.getTextChannel().sendMessage("Missing Permission Boy").queue();
                //TODO error missing permission
                return;
            }
        }
        Utils.executeCommand(command, Utils.getArguments(newMessage, message,command, command.method().getParameters(), controller.toArray(), this));

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
