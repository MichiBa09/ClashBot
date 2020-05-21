package de.michi.clashbot.cocapi.objects;


public class WarPlayer {
    private int townhall;

    private String name;

    private String tag;

    private int mapPos;

    private int firstAttackStars;

    private int firstAttackPercentage;

    private int secondAttackStars;

    private int secondAttackPercentage;

    public WarPlayer(String name, int townhall, String tag, int mapPos, int firstAttackStars, int firstAttackPercentage, int secondAttackStars, int secondAttackPercentage) {
        this.name = name;
        this.tag = tag;
        this.townhall = townhall;
        this.mapPos = mapPos;
        this.firstAttackStars = firstAttackStars;
        this.firstAttackPercentage = firstAttackPercentage;
        this.secondAttackStars = secondAttackStars;
        this.secondAttackPercentage = secondAttackPercentage;
    }

    public String getName() {
        return this.name;
    }

    public String getTag() {
        return this.tag;
    }

    public int getMapPos() {
        return this.mapPos;
    }

    public int getFirstAttackStars() {
        return this.firstAttackStars;
    }

    public int getFirstAttackPercentage() {
        return this.firstAttackPercentage;
    }

    public int getSecondAttackStars() {
        return this.secondAttackStars;
    }

    public int getSecondAttackPercentage() {
        return this.secondAttackPercentage;
    }

    public int getTownhall() {
        return this.townhall;
    }
}