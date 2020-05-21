package de.michi.clashbot.banlists;

import de.michi.clashbot.google.SheetReader;

import java.util.HashMap;
import java.util.List;

public class GMLBanlist {

    private String bansheetid;
    public HashMap bannedPlayers = new HashMap<String, BannedPlayer>();
    public HashMap suspendedPlayers = new HashMap<String, BannedPlayer>();

    public GMLBanlist() {
        this.bansheetid = "1VAuekNjO0H0G3fKVxtLjQSyV5XLwicbarYlekNz1bxU";
    }

    public void update() {
        SheetReader b = new SheetReader(bansheetid, "A2:E500", "Spieler");
        List<List<Object>> banresponse = b.read();
        if (banresponse != null && !banresponse.isEmpty())
            for (List<Object> row : banresponse) {
                if (row == null) return;
                bannedPlayers.put(row.get(0), new BannedPlayer((String) row.get(0), (String) row.get(1), (String) row.get(3), (String) row.get(4)));
            }

        SheetReader sr = new SheetReader(bansheetid, "A2:D500", "Suspendierungen");
        List<List<Object>> response = sr.read();
        if (response != null && !response.isEmpty())
            for (List<Object> row : response) {
                if (row == null) return;
                try {
                    if (row.get(3) != null || (!row.get(3).equals("vorbei"))) {
                        if (!row.get(3).equals("ausgesetzt")) {
                            suspendedPlayers.put(row.get(0), new SuspendedPlayer((String) row.get(0), (String) row.get(1), (String) row.get(2)));
                        }
                    }
                } catch (Exception e) {
                    suspendedPlayers.put(row.get(0), new SuspendedPlayer((String) row.get(0), (String) row.get(1), (String) row.get(2)));
                }

            }
    }

    public boolean checkBan(String tag) {
        return this.bannedPlayers.containsKey(tag);
    }

    public BannedPlayer getBannedPlayer(String tag) {
        return (BannedPlayer) this.bannedPlayers.get(tag);
    }

    public boolean checkSuspended(String tag) {
        return this.suspendedPlayers.containsKey(tag);
    }

    public SuspendedPlayer getSuspendedPlayer(String tag) {
        return (SuspendedPlayer) this.suspendedPlayers.get(tag);
    }

}
