package de.michi.clashbot.commands.utils;

import de.michi.clashbot.listener.CommandListener;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public abstract class Command {

    public String token;
    public String[] args;
    public int minArgs;
    public int maxArgs;
    public int argsAmount;
    public Permission perm;
    public MessageReceivedEvent e;
    public User user;
    public String message;
    public String help;

    public String NO_PERM;
    public String WRONG_ARGS;

    public Command() {
        this.argsAmount = 0;
        this.args = null;
        this.minArgs = -1;
        this.maxArgs = -1;
        this.help = "Keine Hilfe eingetragen.";
        this.perm = Permission.EVERYONE;
        WRONG_ARGS = minArgs != -1 ? "Du musst mindestens " + minArgs + " und maximal " + maxArgs + " Argumente eingeben. :poop:" :
                "Du musst " + argsAmount + " Argumente angeben! :poop:";
        NO_PERM = "Du hast keine Berechtigungen, um diesen Befehl auszuführen. :poop:";

    }

    public void initialise() {
        CommandListener.commands.put(token, this);
    }

    public void action(MessageReceivedEvent e) {
        this.e = e;
        this.user = e.getAuthor();
        this.message = e.getMessage().getContentRaw();
        this.args = Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length);
        if (Permission.hasPerm(Permission.getPermission(user.getId()), perm)) {
            if (minArgs == -1 && maxArgs == -1) {
                if (argsAmount == args.length) {
                    execute();
                } else {
                    e.getChannel().sendMessage("Du musst " + argsAmount + " Argumente angeben. :poop:").queue();
                }
            } else {
                if(minArgs > args.length && minArgs != -1) {
                    e.getChannel().sendMessage("Du musst mindestens " + minArgs + " Argumente angeben. :poop:").queue();
                    return;
                }else if (maxArgs < args.length && maxArgs != -1) {
                    e.getChannel().sendMessage("Du darfst maximal " + maxArgs + " Argumente angeben. :poop:").queue();
                    return;
                }else {
                    execute();
                }

            }
        } else {
            e.getChannel().sendMessage(NO_PERM).queue();
        }
    }


    public void execute() {
    }
}
