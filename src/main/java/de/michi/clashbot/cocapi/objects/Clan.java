package de.michi.clashbot.cocapi.objects;


import java.util.ArrayList;
import java.util.List;

import de.michi.clashbot.cocapi.request.RequestType;
import de.michi.clashbot.cocapi.request.SupercellRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Clan {
    private String tag;

    private List<Player> players;

    private List<String> playerTags;

    private String name;

    private String type;

    private String description;

    private int level;

    public Clan(String clantag) {
        this.tag = clantag;
    }

    public Clan initialize() {
        this.players = new ArrayList<>();
        this.playerTags = new ArrayList<>();
        SupercellRequest sr = new SupercellRequest(this.tag, RequestType.CLAN);
        String response = sr.execute();
        JSONParser parser = new JSONParser();
        if (response == null)
            throw new NullPointerException("Couldn't fetch data for this Clan");
        try {
            JSONObject jobj = (JSONObject) parser.parse(response);
            this.name = (String) jobj.get("name");
            this.type = (String) jobj.get("type");
            this.description = (String) jobj.get("description");
            this.level = Integer.valueOf(String.valueOf(jobj.get("clanLevel"))).intValue();
            JSONArray members = (JSONArray) jobj.get("memberList");
            for (int i = 0; i < members.size(); i++) {
                JSONObject player = (JSONObject) members.get(i);
                this.playerTags.add((String) player.get("tag"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getTag() {
        return this.tag;
    }

    public List<Player> getPlayers() {
        for (String all : getPlayerTags())
            this.players.add((new Player(all)).initialize());
        return this.players;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public int getLevel() {
        return this.level;
    }

    public List<String> getPlayerTags() {
        return this.playerTags;
    }
}