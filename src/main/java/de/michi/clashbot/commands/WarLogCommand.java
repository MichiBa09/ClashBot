package de.michi.clashbot.commands;

import de.michi.clashbot.cocapi.objects.WarLog;
import de.michi.clashbot.commands.utils.Command;
import de.michi.clashbot.commands.utils.Permission;
import de.michi.clashbot.utils.ClanTag;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class WarLogCommand extends Command {

    public WarLogCommand() {
        this.token = "warlog";
        this.help = "Gibt eine Übersicht von dem Warlog";
        this.minArgs = 1;
        this.maxArgs = 2;
        this.perm = Permission.EVERYONE;
    }

    public void execute() {
        if (args.length == 1) {
            WarLog log = new WarLog(ClanTag.getFromString(args[0]), 50).initialize();
            try {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("**Warlog von " + log.getClanName() + "**");
                eb.setThumbnail(log.getLargeBadgeUrl());
                String dc = "**__Übersicht der letzten " + log.getAmountWars() + " Kriege:__**\n\n" + "**Siege: **" + log.getWarsWon() +
                        "\n**Niederlagen:** " + log.getWarsLost() + "\n**Unentschieden:** " + log.getWarsTied() +
                        "\n\n**Siegesrate:** " + log.getWinRate();
                eb.setDescription(dc);
                eb.setColor(Color.GREEN);
                e.getChannel().sendMessage(eb.build()).queue();
            } catch (Exception ex) {
                e.getChannel().sendMessage("Es konnte kein Warlog zu diesem Clan gefunden werden.").queue();
            }
        } else {
            int amountWars = 0;

            try {
                amountWars = Integer.valueOf(args[1]);
            }catch (Exception ex) {
                e.getChannel().sendMessage("Bitte gib eine gültige Zahl an.").queue();
                return;
            }
            WarLog log = new WarLog(ClanTag.getFromString(args[0]), amountWars).initialize();
            try {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("**Warlog von " + log.getClanName() + "**");
                eb.setThumbnail(log.getLargeBadgeUrl());
                String dc = "**__Übersicht der letzten " + log.getAmountWars() + " Kriege:__**\n\n" + "**Siege: **" + log.getWarsWon() +
                        "\n**Niederlagen:** " + log.getWarsLost() + "\n**Unentschieden:** " + log.getWarsTied() +
                        "\n\n**Siegesrate:** " + log.getWinRate();
                eb.setDescription(dc);
                eb.setColor(Color.GREEN);
                e.getChannel().sendMessage(eb.build()).queue();
            } catch (Exception ex) {
                e.getChannel().sendMessage("Es konnte kein Warlog zu diesem Clan gefunden werden.").queue();
            }
        }
    }
}
