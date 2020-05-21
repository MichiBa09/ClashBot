package de.michi.clashbot.banlists;

import java.util.HashMap;

public class SuspendedPlayer {

    public String tag;
    public String name;
    public String enddate;
    public String startdate;

    public SuspendedPlayer(String tag, String name, String enddate, String startdate) {
        this.tag = tag;
        this.name = name;
        this.enddate = enddate;
        this.startdate = startdate;
    }

    public SuspendedPlayer(String tag, String name, String enddate) {
        this.tag = tag;
        this.name = name;
        this.enddate = enddate;
        this.startdate = null;
    }



    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getStartdate() {
        return startdate;
    }
}
