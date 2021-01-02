package dev.nitrocommand.jda4;

import dev.nitrocommand.core.*;
import dev.nitrocommand.core.basic.BasicCommandCore;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JDA4CommandCore extends BasicCommandCore<TextChannel> implements EventListener {
    private final JDA jda;
    private String prefix;
    private final Map<Long, String> customPrefixes = new HashMap<>();
    private MissingPermissionHandler permissionHandler;

    public JDA4CommandCore(JDA jda, String prefix) {
        this.jda = jda;
        this.prefix = prefix;
        jda.addEventListener(this);
        System.out.println(NitroCMD.LOGGER.getName());
        permissionHandler = (jdaController, permission) -> {
            jdaController.getTextChannel().sendMessage("You are missing permission: " + permission.getName()).queue();
        };
    }

    @Override
    public String getName() {
        return "JDA4";
    }

    @Override
    public boolean supportsTabCompleter() {
        return false;
    }

    public void addCustomPrefix(Guild guild, String prefix) {
        customPrefixes.put(guild.getIdLong(), prefix);
    }

    @Override
    public void sendMessage(TextChannel senderObject, String message) {
        senderObject.sendMessage(message).queue();
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
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

        if (newMessage.length() == commandBase.length()) {
            command = object.getBaseExecutor();
        } else {
            newMessage = newMessage.substring(commandBase.length() + 1);
            command = CommandParser.locateSubCommand(newMessage, object);
        }
        if (command == null) {
            command = object.getBaseExecutor();
        }

        Permission permission = JDAUtils.getPermissionForSubCommand(command);
        if (!controller.getAuthor().hasPermission(permission)) {
            permissionHandler.handle(controller, permission);
            return;
        }
        Object[] objects = Utils.getArguments(newMessage, message, command, command.method().getParameters(), controller.toArray(), this);
        if (objects == null) {
            controller.getTextChannel().sendMessage("An Error has occurred. Good luck.").queue();
            return;
        }
        Utils.executeCommand(command, objects);

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

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public JDA getJDA() {
        return jda;
    }

    public MissingPermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public void setPermissionHandler(MissingPermissionHandler permissionHandler) {
        if (permissionHandler == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        this.permissionHandler = permissionHandler;
    }
}
