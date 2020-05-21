package de.michi.clashbot.commands;

import de.michi.clashbot.ClashBot;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;


public class IdentityCommand extends Command {

    public IdentityCommand() {
        this.token = "acc";
        this.argsAmount = 3;
        this.help = "Verwaltet die verschiedenen Accounts";
        this.perm = Permission.COLEADER;
    }

    public void execute() {
        String id = args[1];
        String tag = args[2];

        if (args[0].equalsIgnoreCase("add")) {
            ClashBot.playerList.addPlayer(id, tag);
            e.getChannel().sendMessage("Du hast den Account erfolgreich hinzugefügt.").queue();
            return;
        } else if (args[0].equalsIgnoreCase("remove")) {
            ClashBot.playerList.addPlayer(id, tag);
            e.getChannel().sendMessage("Du hast den Account erfolgreich entfernt.").queue();
            return;

        }
        e.getChannel().sendMessage("Du Depp").queue();
    }
}
