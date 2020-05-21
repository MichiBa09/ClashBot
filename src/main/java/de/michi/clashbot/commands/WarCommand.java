package de.michi.clashbot.commands;

import de.michi.clashbot.cocapi.objects.War;
import de.michi.clashbot.cocapi.objects.WarLog;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;
import de.michi.clashbot.utils.ClanTag;

public class WarCommand extends Command {

    public WarCommand() {
        this.token = "war";
        this.perm = Permission.EVERYONE;
        this.help = "Zeigt infos über den aktuellen Krieg an.";
        this.argsAmount = 1;
    }

    public void execute() {
        try {
            War war = new War(ClanTag.getFromString(args[0])).initialize();

            switch (war.getState()) {
                case "inWar":
                    String wm = "```Der Krieg endet in " + war.getBattleHours() + " Stunden und " + war.getBattleMin() + " Minuten!\n\n" +
                            war.getClanName() + " vs. " + war.getEnemyClanName() + "\n" +
                            "BD: " + war.getBreakdown() + " - " + war.getEnemyBreakdown() + "\n" +
                            war.getPlayers().size() + "er CW\n\n" +
                            war.getStars() + "⭐ ️ - " + war.getEnemyStars() + "⭐️ ️\n" +
                            war.getPercentage() + "% - " + war.getEnemyPercentage() + "%\n```";
                    e.getChannel().sendMessage(wm).queue();
                    break;
                case "warEnded":
                    String em = "```Der Krieg ist zu Ende!\n\n" + war.getClanName() + "   vs.   " + war.getEnemyClanName() + "\nBD: " +
                            war.getBreakdown() + "   -   " + war.getEnemyBreakdown() + "\n\n" +
                            war.getStars() + "⭐️   -   " + war.getEnemyStars() + "⭐️\n" +
                            war.getPercentage() + "%" + "   -  " + war.getPercentage() + "%```";
                    e.getChannel().sendMessage(em).queue();

                    break;
                case "preparation":
                    String pm = "```Der Krieg beginnt in " + war.getPrepHours() + " Stunden und " + war.getPrepMin() + " Minuten!\n\n" +
                            war.getClanName() + "   vs.   " + war.getEnemyClanName() + "\nBD: " +
                            war.getBreakdown() + "   -   " + war.getEnemyBreakdown() + "```";

                    e.getChannel().sendMessage(pm).queue();


                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            e.getChannel().sendMessage("Fehler beim Abrufen der Daten.").queue();
        }
    }


}
