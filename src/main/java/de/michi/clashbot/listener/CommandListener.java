package de.michi.clashbot.listener;

import de.michi.clashbot.ClashBot;
import de.michi.clashbot.commands.utils.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class CommandListener extends ListenerAdapter {

    public static HashMap<String, Command> commands = new HashMap<>();

    public void onMessageReceived(MessageReceivedEvent e) {
        String msg = e.getMessage().getContentRaw();
        if(msg.startsWith(ClashBot.prefix)) {
            if(!e.getAuthor().isBot()){
                String[] args = msg.split(" ");
                for(String all: commands.keySet()) {
                    if(args[0].replace(ClashBot.prefix, "").equalsIgnoreCase(all)) {
                        commands.get(all).action(e);
                    }
                }
            }
        }
    }

}
