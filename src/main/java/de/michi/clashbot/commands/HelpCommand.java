package de.michi.clashbot.commands;

import de.michi.clashbot.ClashBot;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;
import de.michi.clashbot.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class HelpCommand extends Command {

    public HelpCommand() {
        this.token = "help";
        this.perm = Permission.EVERYONE;
        this.help = "Zeigt alle Befehle an";
        this.argsAmount = 0;
    }

    @Override
    public void execute() {
        String msg = "";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.red);
        eb.setTitle("Alle Befehle:");

        for (String name : CommandListener.commands.keySet()) {
            Command cmd = CommandListener.commands.get(name);
            msg += "**"+ ClashBot.prefix + cmd.token + " |** " + cmd.help + "\n";
        }
        eb.setDescription(msg);
        e.getChannel().sendMessage(eb.build()).queue();

    }

}
