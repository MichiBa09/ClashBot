package de.michi.clashbot.cocapi.objects;

import de.michi.clashbot.cocapi.request.RequestType;
import de.michi.clashbot.cocapi.request.SupercellRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DecimalFormat;

public class WarLog {

    public String tag;
    public int amountWars;

    public int warsWon;
    public int warsLost;
    public int warsTied;
    public int wars;

    public String clanname;
    public String clantag;

    public String largeBadgeUrl;


    public WarLog(String tag, int amountWars) {
        this.tag = tag;
        this.amountWars = amountWars;
        this.warsLost = 0;
        this.warsTied = 0;
        this.warsWon = 0;
        this.wars = 0;
    }

    public WarLog initialize() {
        String response = new SupercellRequest(this.tag, RequestType.WARLOG).execute();
        JSONParser parser = new JSONParser();
        if (response != null) {
            try {
                JSONArray wars = (JSONArray) ((JSONObject) parser.parse(response)).get("items");
                JSONObject obj = (JSONObject) ((JSONObject) wars.get(0)).get("clan");
                this.clanname = (String) obj.get("name");
                this.clantag = (String) obj.get("tag");



                JSONObject badges = (JSONObject) obj.get("badgeUrls");
                this.largeBadgeUrl = (String) badges.get("large");
                int toAdd = 0;

                for (int i = 0; i < amountWars + 1; i++) {

                    try {
                        JSONObject war = (JSONObject) wars.get(i);
                        if (war != null) {
                            String result = (String) war.get("result");
                            if (result != null) {

                                JSONObject op = (JSONObject) war.get("opponent");
                                if(op.get("name") == "null") {
                                    toAdd++;
                                    continue;
                                }
                                switch (result) {
                                    case "win":
                                        warsWon++;
                                        this.wars++;
                                        break;
                                    case "tie":
                                        try {
                                            if (((double) war.get("destructionPercentage")) > 100) {
                                                this.wars--;
                                                toAdd++;
                                                continue;
                                            }
                                            warsTied++;
                                            this.wars++;
                                            break;
                                        }catch(Exception e) {
                                            toAdd--;
                                            warsTied++;
                                            this.wars++;
                                            break;
                                        }
                                    case "lose":
                                        warsLost++;
                                        this.wars++;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            this.wars = amountWars;
                            return this;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i <  toAdd +1; i++) {

                    try {
                        JSONObject war = (JSONObject) wars.get(amountWars + i);
                        if (war != null) {
                            String result = (String) war.get("result");
                            if (result != null) {

                                JSONObject op = (JSONObject) war.get("opponent");
                                this.wars++;
                                switch (result) {
                                    case "win":
                                        warsWon++;
                                        break;
                                    case "tie":
                                        if(((double)war.get("destructionPercentage")) > 100) {
                                            this.wars--;
                                            toAdd++;
                                            continue;
                                        }
                                        warsTied++;
                                        break;
                                    case "lose":
                                        warsLost++;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else {
                            this.wars = amountWars;
                            return this;
                        }

                    } catch (Exception e) {
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Couldn't fetch data for this clan.");
            return null;
        }

        return this;
    }

    public int getWarsWon() {
        return this.warsWon;
    }

    public int getWarsTied() {
        return this.warsTied;
    }

    public int getWarsLost() {
        return this.warsLost;
    }

    public String getWinRate() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(((double) warsWon / wars) * 100) + "%";
    }

    public String getClanName() {
        return this.clanname;
    }

    public String getClanTag() {
        return this.clantag;
    }

    public String getLargeBadgeUrl() {
        return this.largeBadgeUrl;
    }

    public int getAmountWars() {
        return this.wars;
    }

}
