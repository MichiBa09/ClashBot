package de.michi.clashbot.banlists;

public class BannedPlayer {

    public String tag;
    public String name;
    public String reason;
    public String bannedSince;

    public BannedPlayer(String tag, String name, String reason, String bannedSince) {
        this.tag = tag;
        this.name = name;
        this.reason = reason;
        this.bannedSince = bannedSince;
    }

    public BannedPlayer(String tag, String name, String bannedSince) {
        this.tag = tag;
        this.name = name;
        this.reason = "";
        this.bannedSince = bannedSince;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public String getBannedSince() {
        return bannedSince;
    }
}
