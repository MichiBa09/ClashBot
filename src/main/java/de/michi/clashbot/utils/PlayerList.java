package de.michi.clashbot.utils;

import de.michi.clashbot.google.SheetReader;
import de.michi.clashbot.google.SheetWriter;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {

    private String sheetID;
    private SheetReader sr;
    private SheetWriter sw;


    public PlayerList() {
        this.sheetID = "1JRhqbkcitPacY3eo4vLlhoP4pCrEenlYGnVtwg6e5Dk";
        sr = new SheetReader(sheetID, "A2:B200", "list");
    }


    public void addPlayer(String id, String tag) {
        List<List<Object>> response = sr.read();
        if (response != null && !response.isEmpty())
            for (List<Object> row : response) {
                if (row.size() != 0) {
                    if (row.get(0).equals(id)) {
                        sw = new SheetWriter(sheetID, "B" + (response.indexOf(row) + 2), "list");
                        sw.write(row.get(1) + ";" + tag);
                        return;
                    }
                }
            }
        sw = new SheetWriter(sheetID, "B" + (response.size() + 2), "list");
        sw.write(tag);
        sw = new SheetWriter(sheetID, "A" + (response.size() + 2), "list");
        sw.write(id);
    }

    public List<String> getPlayerTags(String id) {
        List<List<Object>> response = sr.read();
        List<String> tags = new ArrayList<>();
        if (response != null && !response.isEmpty())
            for (List<Object> row : response) {
                if (row.size() != 0) {
                    try {
                        row.get(1);
                    } catch (Exception e) {
                        return tags;
                    }
                    if (row.get(0).equals(id)) {
                        String[] tagarray = ((String) row.get(1)).split(";");
                        for (int i = 0; i < tagarray.length; i++) {
                            tags.add(tagarray[i]);
                        }
                    }
                }
            }
        return tags;
    }


    public void deleteTag(String id, String tag) {
        List<List<Object>> response = sr.read();
        if (response != null && !response.isEmpty())
            for (List<Object> row : response) {
                if (row.size() != 0) {
                    if (row.get(0).equals(id)) {
                        String[] tagarray = ((String) row.get(1)).split(";");
                        String newNames = "";
                        for (int i = 0; i < tagarray.length; i++) {
                            if (!tagarray[i].equalsIgnoreCase(tag)) newNames += tagarray[i] + ";";
                        }
                        sw = new SheetWriter(sheetID, "B" + (response.indexOf(row) + 2), "list");
                        sw.write(newNames);
                        break;
                    }
                }
            }
    }
}
