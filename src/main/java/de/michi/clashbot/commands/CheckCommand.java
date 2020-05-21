package de.michi.clashbot.commands;

import de.michi.clashbot.ClashBot;
import de.michi.clashbot.banlists.*;
import de.michi.clashbot.cocapi.objects.Player;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;

public class CheckCommand extends Command {

    public CheckCommand() {
        this.token = "check";
        this.argsAmount = 1;
        this.help = "Checkt einen Spieler, ob er gebannt ist.";
        this.perm = Permission.MEMBER;
    }

    public void execute() {
        if (!args[0].startsWith("#")) {
            e.getChannel().sendMessage("Bitte gib einen gültigen tag ein.").queue();
            return;
        }

        String msg = "";
        try {
            Player p = new Player(args[0]).initialize();
            msg += "**__Überprüfe Spieler " + p.getName() + "__**\n\n";
        } catch (Exception ex) {
            e.getChannel().sendMessage("Dieser Spieler konnte nicht gefunden werden.").queue();
            return;
        }

        String tag = args[0];
        GMLBanlist gml = ClashBot.gmlBanlist;
        CWLBanlist cwl = ClashBot.cwlBanlist;
        NDLBanlist ndl = ClashBot.ndlBanlist;
        MLCWBanlist mlcw = ClashBot.mlcwBanlist;


        BannedPlayer bp = null;
        SuspendedPlayer sp = null;
        if (gml.checkBan(tag)) {
            bp = gml.getBannedPlayer(tag);
            msg += ":x: Der Spieler ist von der GML seit " + bp.getBannedSince() + " gebannt. Grund: " + bp.getReason() + "\n";
        } else {
            msg += ":white_check_mark: Der Spieler ist nicht von der GML gebannt.\n";
        }
        if (gml.checkSuspended(tag)) {
            sp = gml.getSuspendedPlayer(tag);
            msg += ":x: Der Spieler ist von der GML suspendiert. Ende: " + sp.getEnddate() + ".\n";
        } else {
            msg += ":white_check_mark: Der Spieler ist nicht von der GML suspendiert.\n";
        }

        if (cwl.checkBan(tag)) {
            bp = cwl.getBannedPlayer(tag);
            msg += ":x: Der Spieler ist von der CWL seit " + bp.getBannedSince() + " gebannt.\n";
        } else {
            msg += ":white_check_mark: Der Spieler ist nicht von der CWL gebannt.\n";
        }
        if (cwl.checkSuspended(tag)) {
            sp = cwl.getSuspendedPlayer(tag);
            msg += ":x: Der Spieler ist von der CWL suspendiert. Ende: " + sp.getEnddate() + ".\n";
        } else {
            msg += ":white_check_mark: Der Spieler ist nicht von der CWL suspendiert.\n";
        }

        if (ndl.checkBan(tag)) {
            bp = ndl.getBannedPlayer(tag);
            msg += ":x: Der Spieler ist von der NDL gebannt.\n";
        } else {
            msg += ":white_check_mark: Der Spieler ist nicht von der NDL gebannt.\n";
        }

        if (mlcw.checkBan(tag)) {
            mlcw.getBannedPlayer(tag);
            msg += ":x: Der Spieler ist seit " + bp.getBannedSince() + " von der MLCW gebannt.\n";
        } else {
            msg += ":white_check_mark: Der Spieler ist nicht von der MLCW gebannt.\n";
        }


        e.getChannel().sendMessage(msg).queue();
    }


}
