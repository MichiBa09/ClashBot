package de.michi.clashbot.commands;

import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;

public class PingCommand extends Command {

    public PingCommand() {
        this.token = "ping";
        this.perm = Permission.EVERYONE;
        this.help = "Pong!";
    }

    public void execute() {
        e.getChannel().sendMessage("Pong!").queue();
    }
}
