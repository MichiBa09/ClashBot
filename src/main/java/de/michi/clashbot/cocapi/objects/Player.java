package de.michi.clashbot.cocapi.objects;


import de.michi.clashbot.cocapi.request.RequestType;
import de.michi.clashbot.cocapi.request.SupercellRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Player {
    private String tag;

    private String name;

    private String clanName;

    private String clanTag;

    private String townhall;

    private String role;

    private int clanLevel;

    private int level;

    private int trophies;

    private int bestTrophies;

    private int warStars;

    private int donations;

    private int donationsReceived;

    public Player(String tag) {
        this.tag = tag;
    }

    public Player initialize() {
        String response = (new SupercellRequest(this.tag, RequestType.PLAYER)).execute();
        JSONParser parser = new JSONParser();
        if (response != null) {
            try {
                JSONObject jobj = (JSONObject) parser.parse(response.toString());
                this.townhall = String.valueOf(jobj.get("townHallLevel"));
                JSONObject clanObj = (JSONObject) jobj.get("clan");
                this.name = (String) jobj.get("name");
                this.role = (String) jobj.get("role");
                this.level = Integer.valueOf(String.valueOf(jobj.get("expLevel"))).intValue();
                this.trophies = Integer.valueOf(String.valueOf(jobj.get("trophies"))).intValue();
                this.bestTrophies = Integer.valueOf(String.valueOf(jobj.get("bestTrophies"))).intValue();
                this.warStars = Integer.valueOf(String.valueOf(jobj.get("warStars"))).intValue();
                this.donations = Integer.valueOf(String.valueOf(jobj.get("donations"))).intValue();
                this.donationsReceived = Integer.valueOf(String.valueOf(jobj.get("donationsReceived"))).intValue();
                if (clanObj != null) {
                    this.clanName = (String) clanObj.get("name");
                    this.clanTag = (String) clanObj.get("tag");
                    this.clanLevel = Integer.parseInt(String.valueOf(clanObj.get("clanLevel")));
                } else {
                    this.clanName = null;
                    this.clanTag = null;
                }
            } catch (ParseException e) {
                throw new NullPointerException("Couldn't fetch data for this Player.");
            }
        } else {
            return null;
        }
        return this;
    }

    public String getTag() {
        return this.tag;
    }

    public String getName() {
        return this.name;
    }

    public String getClanName() {
        return this.clanName;
    }

    public String getClanTag() {
        return this.clanTag;
    }

    public String getTownhall() {
        return this.townhall;
    }

    public int getClanLevel() {
        return this.clanLevel;
    }

    public int getLevel() {
        return this.level;
    }

    public int getTrophies() {
        return this.trophies;
    }

    public int getBestTrophies() {
        return this.bestTrophies;
    }

    public int getWarStars() {
        return this.warStars;
    }

    public String getRole() {
        return this.role;
    }

    public int getDonations() {
        return this.donations;
    }

    public int getDonationsReceived() {
        return this.donationsReceived;
    }
}