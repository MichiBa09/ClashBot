package de.michi.clashbot.utils;

public class ClanTag {

    public static String getFromString(String input) {

        switch (input.toLowerCase()){
            case "be": return "#2VRP0YRC";
            case "gf": return "#2LC2V8JY";
            case "sc": return "#9P2CY90U";
            case "ms2": return "#8RVQ2RJR";
            case "bc": return "#U0LP280";
            default: return input;
        }
    }
}
