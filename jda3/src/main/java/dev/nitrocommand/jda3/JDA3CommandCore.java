package dev.nitrocommand.jda3;

import dev.nitrocommand.core.BasicCommandParser;
import dev.nitrocommand.core.NitroCMD;
import dev.nitrocommand.core.NitroSubCommand;
import dev.nitrocommand.core.Utils;
import dev.nitrocommand.core.basic.BasicCommandCore;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.lang.annotation.Retention;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JDA3CommandCore extends BasicCommandCore implements EventListener {
    private JDA jda;
    private String prefix = "/";
    private Map<Long, String> customPrefixes = new HashMap<>();

    public JDA3CommandCore(JDA jda, String prefix) {
        this.jda = jda;
        this.prefix = prefix;
        jda.addEventListener(this);
    }

    @Override
    public String getName() {
        return "JDA3";
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent messageEvent = (GuildMessageReceivedEvent) event;
            if (isCommand(messageEvent.getMessage().getContentRaw())) {
                String stripContent = stripCommand(messageEvent.getMessage().getContentRaw());
                executeCommand(stripContent, new JDAController(messageEvent));
            }
        }
    }

    private void executeCommand(String message, JDAController controller) {
        if(!parser.doesCommandExist(message.split(" ")[0])){
            NitroCMD.LOGGER.debug("No base command found: "+ message);
            return;
        }
        NitroSubCommand command = parser.locateCommand(message);
        if (command == null) {
            NitroCMD.LOGGER.debug("Command Not Found: "+ message);
            return;
        }
        if (!command.requiredPermission().isEmpty()) {
            Permission permission = Arrays.stream(Permission.values()).filter(p -> p.name().equalsIgnoreCase(command.requiredPermission())).findFirst().orElse(Permission.MESSAGE_WRITE);
            if (!controller.getAuthor().hasPermission(permission)) {
                controller.getTextChannel().sendMessage("Missing Permission Boy").queue();
                //TODO error missing permission
                return;
            }
        }
        parser.executeCommand(command, Utils.getArguments(message, command, command.method().getParameters(), controller.toArray(), this));

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
