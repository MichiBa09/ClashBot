package de.michi.clashbot.commands;

import de.michi.clashbot.ClashBot;
import de.michi.clashbot.cocapi.objects.Player;
import de.michi.clashbot.cocapi.objects.War;
import de.michi.clashbot.cocapi.objects.WarPlayer;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;

import java.util.List;

public class WarInfoCommand extends Command {

    public WarInfoCommand() {
        this.token = "warinfo";
        this.argsAmount = 0;
        this.perm = Permission.MEMBER;
        this.help = "Zeigt dir an, wo du noch Angriffe offen hast.";
    }

    public void execute() {
        String id = e.getAuthor().getId();
        List<String> tags = ClashBot.playerList.getPlayerTags(id);
        String msg = "";
        for (String tag : tags) {
            Player p = new Player(tag).initialize();
            if (p.getClanTag() != null) {
                try {
                    War war = new War(p.getClanTag()).initialize();
                    boolean inWar = false;
                    for (WarPlayer wp : war.getWarPlayers()) {
                        if (wp.getTag().equals(p.getTag())) {
                            inWar = true;
                            String state = war.getState();
                            if (!state.equalsIgnoreCase("warEnded")) {
                                if (wp.getFirstAttackStars() == -1 && wp.getFirstAttackPercentage() == -1) {
                                    msg += "**2** offene Angriffe im Clan **" + p.getClanName() + "** mit **" + p.getName() + "**.\n";
                                    if (state.equals("preparation")) {
                                        msg += "Der Krieg beginnt in **" + war.getPrepHours() + " Stunden und " + war.getPrepMin() + " Minuten.**\n\n";
                                    } else {
                                        msg += "Der Krieg endet in **" + war.getBattleHours() + " Stunden und " + war.getBattleMin() + " Minuten.**\n\n";
                                    }
                                } else if (wp.getSecondAttackStars() == -1 && wp.getSecondAttackPercentage() == -1) {
                                    msg += "**1** offener Angriff im Clan **" + p.getClanName() + "** mit **" + p.getName() + "**.\n";
                                    if (state.equals("preparation")) {
                                        msg += "Der Krieg beginnt in **" + war.getPrepHours() + " Stunden und " + war.getPrepMin() + " Minuten.**\n\n";
                                    } else {
                                        msg += "Der Krieg endet in **" + war.getBattleHours() + " Stunden und " + war.getBattleMin() + " Minuten.**\n\n";
                                    }
                                } else {
                                    msg += "Keine offene Angriffe mit **" + p.getName() + "**.\n\n";
                                }
                            } else {
                                msg += "Keine offene Angriffe mit **" + p.getName() + "**.\n\n";
                            }
                        }

                    }
                    if (!inWar) {
                        msg += "Der Account **" + p.getName() + "** ist **nicht** bei **" + p.getClanName() + "** im Krieg.\n\n";

                    }
                } catch (Exception ex) {
                    msg += "Der Clanlog im Clan von " + p.getName() + " ist nicht öffentlich.\n\n";
                }
            } else {
                msg += "Der Account " + p.getClanName() + " ist aktuell in keinem Krieg.\n\n";
            }
        }
        msg = msg == "" ? "Es wurde bisher kein Account mit deinem Discord Account verbunden." : msg;
        e.getChannel().sendMessage(msg).queue();

    }

}
