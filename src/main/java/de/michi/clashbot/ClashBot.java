package de.michi.clashbot;

import com.google.api.services.sheets.v4.Sheets;
import de.michi.clashbot.banlists.CWLBanlist;
import de.michi.clashbot.banlists.GMLBanlist;
import de.michi.clashbot.banlists.MLCWBanlist;
import de.michi.clashbot.banlists.NDLBanlist;
import de.michi.clashbot.commands.*;
import de.michi.clashbot.listener.CommandListener;
import de.michi.clashbot.utils.PlayerList;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Timer;
import java.util.TimerTask;

public class ClashBot extends ListenerAdapter {

    public static JDA jda;
    public static Sheets service;
    public static String prefix;
    public static String supercellKey;
    public static PlayerList playerList;
    public static CWLBanlist cwlBanlist;
    public static MLCWBanlist mlcwBanlist;
    public static NDLBanlist ndlBanlist;
    public static GMLBanlist gmlBanlist;

    public static void main(String... args) throws LoginException {
        prefix = ".";
        supercellKey = args[1];
        JDABuilder builder = new JDABuilder(args[0]);
        builder.setActivity(Activity.playing(".help"));
        builder.setAutoReconnect(true);
        jda = builder.build();
        jda.addEventListener(new CommandListener());
        addCommands();
        playerList = new PlayerList();
        cwlBanlist = new CWLBanlist();
        mlcwBanlist = new MLCWBanlist();
        ndlBanlist = new NDLBanlist();
        gmlBanlist = new GMLBanlist();
        startTimer();



    }

    public static void addCommands() {
        new HelpCommand().initialise();
        new WarLogCommand().initialise();
        new WarCommand().initialise();
        new CheckCommand().initialise();
        new ClanCheckCommand().initialise();
        new IdentityCommand().initialise();
        new WarInfoCommand().initialise();
        new PingCommand().initialise();
    }

    public static void startTimer() {
        Timer timer = new Timer();
        TimerTask hourlyTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Update alle Bannlisten...");
                cwlBanlist.update();
                gmlBanlist.update();
                ndlBanlist.update();
                mlcwBanlist.update();
            }
        };
        timer.schedule(hourlyTask, 0l, 1000 * 60 * 60);
    }
}
