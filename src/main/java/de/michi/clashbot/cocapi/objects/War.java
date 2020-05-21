package de.michi.clashbot.cocapi.objects;

import de.michi.clashbot.cocapi.request.RequestType;
import de.michi.clashbot.cocapi.request.SupercellRequest;
import javafx.geometry.HorizontalDirection;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.ReadableInstant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class War {
    private String tag;

    private String state;

    private int teamSize;

    private String clanTag;

    private String clanName;

    private Long stars;

    private String percentage;

    private String enemyClanTag;

    private String enemyClanName;

    private Long enemyStars;

    private String enemyPercentage;

    private List<String> players;

    private List<String> enemyPlayers;

    private List<String> playersName;

    private List<String> enemyPlayersName;

    private List<WarPlayer> warPlayers;

    private List<WarPlayer> enemyWarPlayers;

    private HashMap<Integer, Integer> townhalls;

    private HashMap<Integer, Integer> enemyTownhalls;

    private Date preparationStartTime;

    private Date startTime;

    private Date endTime;

    public String largeBadgeUrl;

    public War(String clantag) {
        this.tag = clantag;
    }

    public War initialize() {
        DecimalFormat df = new DecimalFormat("0.00");
        this.players = new ArrayList<>();
        this.enemyPlayers = new ArrayList<>();
        this.playersName = new ArrayList<>();
        this.warPlayers = new ArrayList<>();
        this.enemyWarPlayers = new ArrayList<>();
        this.enemyPlayersName = new ArrayList<>();
        this.townhalls = new HashMap<>();
        this.enemyTownhalls = new HashMap<>();
        this.townhalls = new HashMap<>();
        this.townhalls.put(Integer.valueOf(13), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(12), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(11), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(10), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(9), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(8), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(7), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(6), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(5), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(4), Integer.valueOf(0));
        this.townhalls.put(Integer.valueOf(3), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(13), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(12), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(11), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(10), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(9), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(8), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(7), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(6), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(5), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(4), Integer.valueOf(0));
        this.enemyTownhalls.put(Integer.valueOf(3), Integer.valueOf(0));
        String response = (new SupercellRequest(this.tag, RequestType.CURRENTWAR)).execute();
        JSONParser parser = new JSONParser();
        if (response != null)
            try {
                JSONObject jobj = (JSONObject) parser.parse(response);
                this.state = (String) jobj.get("state");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {
                    this.preparationStartTime = sdf.parse((String) jobj.get("preparationStartTime"));
                    this.startTime = sdf.parse((String) jobj.get("startTime"));
                    this.endTime = sdf.parse((String) jobj.get("endTime"));
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error");
                }
                this.teamSize = Integer.parseInt(String.valueOf(jobj.get("teamSize")));
                JSONObject clanObj = (JSONObject) jobj.get("clan");
                this.clanName = (String) clanObj.get("name");
                this.clanTag = (String) clanObj.get("tag");
                this.stars = (Long) clanObj.get("stars");
                this.percentage = df.format((Double) clanObj.get("destructionPercentage"));

                JSONObject badges = (JSONObject) clanObj.get("badgeUrls");
                this.largeBadgeUrl = (String) badges.get("large");

                JSONObject enemyClanObj = (JSONObject) jobj.get("opponent");
                this.enemyClanName = (String) enemyClanObj.get("name");
                this.enemyClanTag = (String) enemyClanObj.get("tag");
                this.enemyStars = (Long) enemyClanObj.get("stars");
                this.enemyPercentage = df.format((Double) enemyClanObj.get("destructionPercentage"));
                JSONArray players = (JSONArray) clanObj.get("members");
                JSONArray enemyPlayers = (JSONArray) enemyClanObj.get("members");
                int i;
                for (i = 0; i < players.size(); i++) {
                    JSONObject player = (JSONObject) players.get(i);
                    String tag = (String) player.get("tag");
                    String name = (String) player.get("name");
                    this.playersName.add(name);
                    this.players.add(tag);
                    addTownhall((Long) player.get("townhallLevel"));
                    JSONArray attacks = (JSONArray) player.get("attacks");
                    int firstStars = -1;
                    int secondStars = -1;
                    int firstPercentage = -1;
                    int secondPercentage = -1;
                    if (attacks != null) {
                        for (int at = 0; at < attacks.size(); at++) {
                            JSONObject attack = (JSONObject) attacks.get(at);
                            if (at == 0) {
                                firstStars = Integer.valueOf(String.valueOf(attack.get("stars"))).intValue();
                                firstPercentage = Integer.valueOf(String.valueOf(attack.get("destructionPercentage"))).intValue();
                            } else {
                                secondStars = Integer.valueOf(String.valueOf(attack.get("stars"))).intValue();
                                secondPercentage = Integer.valueOf(String.valueOf(attack.get("destructionPercentage"))).intValue();
                            }
                        }
                    }
                    this.warPlayers.add(new WarPlayer(name, Integer.valueOf(String.valueOf(player.get("townhallLevel"))).intValue(), tag, Integer.valueOf(String.valueOf(player.get("mapPosition"))).intValue(), firstStars, firstPercentage, secondStars, secondPercentage));
                }
                for (i = 0; i < enemyPlayers.size(); i++) {
                    JSONObject player = (JSONObject) enemyPlayers.get(i);
                    String tag = (String) player.get("tag");
                    String name = (String) player.get("name");
                    this.enemyPlayersName.add(name);
                    this.enemyPlayers.add(tag);
                    addEnemyTownhall((Long) player.get("townhallLevel"));
                    JSONArray attacks = (JSONArray) player.get("attacks");
                    int firstStars = -1;
                    int secondStars = -1;
                    int firstPercentage = -1;
                    int secondPercentage = -1;

                    if (attacks != null) {
                        for (int at = 0; at < attacks.size(); at++) {
                            JSONObject attack = (JSONObject) attacks.get(at);
                            if (at == 0) {
                                firstStars = Integer.valueOf(String.valueOf(attack.get("stars"))).intValue();
                                firstPercentage = Integer.valueOf(String.valueOf(attack.get("destructionPercentage"))).intValue();
                            } else {
                                secondStars = Integer.valueOf(String.valueOf(attack.get("stars"))).intValue();
                                secondPercentage = Integer.valueOf(String.valueOf(attack.get("destructionPercentage"))).intValue();
                            }
                        }
                    }
                    this.enemyWarPlayers.add(new WarPlayer(name, Integer.valueOf(String.valueOf(player.get("townhallLevel"))).intValue(), tag, Integer.valueOf(String.valueOf(player.get("mapPosition"))).intValue(), firstStars, firstPercentage, secondStars, secondPercentage));
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        return this;
    }

    private void addTownhall(Long th) {
        Integer townhalllevel = Integer.valueOf(Math.toIntExact(th.longValue()));
        try {
            this.townhalls.put(townhalllevel, Integer.valueOf((this.townhalls.get(townhalllevel)).intValue() + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addEnemyTownhall(Long th) {
        Integer townhalllevel = Integer.valueOf(Math.toIntExact(th.longValue()));
        this.enemyTownhalls.put(townhalllevel, Integer.valueOf(((Integer) this.enemyTownhalls.get(townhalllevel)).intValue() + 1));
    }

    public String getClanTag() {
        return this.clanTag;
    }

    public String getClanName() {
        return this.clanName;
    }

    public Long getStars() {
        return this.stars;
    }

    public String getPercentage() {
        return this.percentage;
    }

    public String getEnemyClanTag() {
        return this.enemyClanTag;
    }

    public String getEnemyClanName() {
        return this.enemyClanName;
    }

    public Long getEnemyStars() {
        return this.enemyStars;
    }

    public String getEnemyPercentage() {
        return this.enemyPercentage;
    }

    public String getState() {
        return this.state;
    }

    public int getTeamSize() {
        return this.teamSize;
    }

    public List<String> getPlayerTags() {
        return this.players;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (String p : getPlayerTags())
            players.add((new Player(p)).initialize());
        return players;
    }

    public List<String> getEnemyPlayers() {
        return this.enemyPlayers;
    }

    public List<String> getPlayersNames() {
        return this.playersName;
    }

    public List<String> getEnemyPlayersNames() {
        return this.enemyPlayersName;
    }

    public Date getPreparationStartTime() {
        return this.preparationStartTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public String getBreakdown() {
        StringBuilder builder = new StringBuilder();
        for (int i = 13; i > 3; i--) {
            if (i > 9) {
                if (this.townhalls.get(Integer.valueOf(i)) != null) {
                    builder.append(this.townhalls.get(Integer.valueOf(i))).append("/");
                } else {
                    builder.append("0/");
                }
            } else {
                if (this.townhalls.get(Integer.valueOf(i)) != 0) {
                    builder.append(this.townhalls.get(Integer.valueOf(i))).append("/");
                }
            }
        }
        String toReturn = builder.toString();
        if (toReturn.substring(toReturn.length() -1).equals("/")) {
            toReturn = toReturn.substring(0, toReturn.length() - 1);
        }
        return toReturn;
    }

    public String getEnemyBreakdown() {
        StringBuilder builder = new StringBuilder();
        for (int i = 13; i > 3; i--) {
            if (i > 9) {
                if (this.enemyTownhalls.get(Integer.valueOf(i)) != null) {
                    builder.append(this.enemyTownhalls.get(Integer.valueOf(i))).append("/");
                } else {
                    builder.append("0/");
                }
            } else {
                if (this.enemyTownhalls.get(Integer.valueOf(i)) != 0) {
                    builder.append(this.enemyTownhalls.get(Integer.valueOf(i))).append("/");
                }
            }
        }
        String toReturn = builder.toString();
        if (toReturn.substring(toReturn.length() -1).equals("/")) {
            toReturn = toReturn.substring(0, toReturn.length() - 1);
        }
        return toReturn;
    }

    public int getTownhallAmount(int townhallLevel) {
        return (this.townhalls.get(Integer.valueOf(townhallLevel)) != null) ? ((Integer) this.townhalls.get(Integer.valueOf(townhallLevel))).intValue() : 0;
    }

    public int getEnemyTownhallAmount(int townhallLevel) {
        return (this.enemyTownhalls.get(Integer.valueOf(townhallLevel)) != null) ? ((Integer) this.enemyTownhalls.get(Integer.valueOf(townhallLevel))).intValue() : 0;
    }

    public String getPrepLength() {
        DateTime dt1 = new DateTime(getPreparationStartTime());
        DateTime dt2 = new DateTime(getStartTime());
        if (Hours.hoursBetween((ReadableInstant) dt1, (ReadableInstant) dt2).getHours() == 0)
            return Minutes.minutesBetween((ReadableInstant) dt1, (ReadableInstant) dt2).getMinutes() + "min";
        return Hours.hoursBetween((ReadableInstant) dt1, (ReadableInstant) dt2).getHours() + "h";
    }

    public String getBattleLength() {
        DateTime dt1 = new DateTime(getStartTime());
        DateTime dt2 = new DateTime(getEndTime());
        if (Hours.hoursBetween((ReadableInstant) dt1, (ReadableInstant) dt2).getHours() == 0)
            return Minutes.minutesBetween((ReadableInstant) dt1, (ReadableInstant) dt2).getMinutes() + "min";
        return Hours.hoursBetween((ReadableInstant) dt1, (ReadableInstant) dt2).getHours() + "h";
    }

    public List<String> getEnemyPlayersName() {
        return this.enemyPlayersName;
    }

    public List<WarPlayer> getWarPlayers() {
        return this.warPlayers;
    }

    public String getLargeBadgeUrl() {
        return this.largeBadgeUrl;
    }

    public int getPrepHours() {
        return Hours.hoursBetween(new DateTime(), new DateTime(getStartTime())).getHours();

    }

    public int getPrepMin() {
        return ((Minutes.minutesBetween(new DateTime(), new DateTime(getStartTime())).getMinutes()) % 60);
    }

    public int getBattleHours() {
        return Hours.hoursBetween(new DateTime(), new DateTime(getEndTime())).getHours();
    }

    public int getBattleMin() {
        return 60 - ((Minutes.minutesBetween(new DateTime(), new DateTime(getEndTime())).getMinutes()) % 60);
    }
}