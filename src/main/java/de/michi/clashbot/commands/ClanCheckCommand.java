package de.michi.clashbot.commands;

import de.michi.clashbot.ClashBot;
import de.michi.clashbot.banlists.*;
import de.michi.clashbot.cocapi.objects.Clan;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;
import de.michi.clashbot.utils.ClanTag;

public class ClanCheckCommand extends Command {

    public ClanCheckCommand() {
        this.token = "clancheck";
        this.help = "Checkt einen ganzen Clan nach gebannten Spielern";
        this.argsAmount = 1;
        this.perm = Permission.MEMBER;
    }

    public void execute() {
        String clantag = ClanTag.getFromString(args[0]);
        if (!clantag.startsWith("#")) {
            e.getChannel().sendMessage("Bitte gib einen gültigen tag ein.").queue();
            return;
        }

        String msg = "";
        Clan clan = null;
        try {
            clan = new Clan(clantag).initialize();
            msg += "**__Überprüfe Spieler " + clan.getName() + "__**\n\n";
        } catch (Exception ex) {
            e.getChannel().sendMessage("Dieser Clan konnte nicht gefunden werden.").queue();
            return;
        }

        GMLBanlist gml = ClashBot.gmlBanlist;
        CWLBanlist cwl = ClashBot.cwlBanlist;
        NDLBanlist ndl = ClashBot.ndlBanlist;
        MLCWBanlist mlcw = ClashBot.mlcwBanlist;

        boolean hasBannedPlayer = false;

        for (String tag : clan.getPlayerTags()) {

            BannedPlayer bp = null;
            SuspendedPlayer sp = null;
            if (gml.checkBan(tag)) {
                bp = gml.getBannedPlayer(tag);
                msg += ":x: Der Spieler " + bp.getName() + "( " + bp.getTag() + ") ist von der GML seit " + bp.getBannedSince() + " gebannt. Grund: " + bp.getReason() + "\n";
                hasBannedPlayer = true;
            }
            if (gml.checkSuspended(tag)) {
                sp = gml.getSuspendedPlayer(tag);
                msg += ":x: Der Spieler " + sp.getName() + "( " + sp.getTag() + ") ist von der GML suspendiert. Ende: " + sp.getEnddate() + ".\n";
                hasBannedPlayer = true;
            }

            if (cwl.checkBan(tag)) {
                bp = cwl.getBannedPlayer(tag);
                msg += ":x: Der Spieler " + bp.getName() + "( " + bp.getTag() + ")ist von der CWL seit " + bp.getBannedSince() + " gebannt.\n";
                hasBannedPlayer = true;
            }
            if (cwl.checkSuspended(tag)) {
                sp = cwl.getSuspendedPlayer(tag);
                msg += ":x: Der Spieler " + sp.getName() + "( " + sp.getTag() + ")ist von der CWL suspendiert. Ende: " + sp.getEnddate() + ".\n";
                hasBannedPlayer = true;
            }

            if (ndl.checkBan(tag)) {
                bp = ndl.getBannedPlayer(tag);
                msg += ":x: Der Spieler " + bp.getName() + "( " + bp.getTag() + ")ist von der NDL gebannt.\n";
                hasBannedPlayer = true;
            }

            if (mlcw.checkBan(tag)) {
                bp = mlcw.getBannedPlayer(tag);
                msg += ":x: Der Spieler " + bp.getName() + "( " + bp.getTag() + ")ist seit " + bp.getBannedSince() + " von der MLCW gebannt.\n";
                hasBannedPlayer = true;
            }


        }
        msg+= "\nDer Clan wurde vollständig überprüft.";
        if(!hasBannedPlayer) {
            msg += "\nEs wurden keine gebannten oder suspendierten Spieler im Clan gefunden.";
        }
        e.getChannel().sendMessage(msg).queue();

    }
}
