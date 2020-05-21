package de.michi.clashbot.banlists;

import de.michi.clashbot.google.SheetReader;

import java.util.HashMap;
import java.util.List;

public class CWLBanlist {

    private String sheetid;
    public HashMap bannedPlayers = new HashMap<String, BannedPlayer>();
    public HashMap suspendedPlayers = new HashMap<String, SuspendedPlayer>();

    public CWLBanlist() {
        this.sheetid = "1naROLOYzLkRUUqGMAqy2i31vWw5NOF8ETmMaVPyiM0M";
    }

    public void update() {
        SheetReader br = new SheetReader(sheetid, "A3:C1000", "BANS");
        List<List<Object>> banresponse = br.read();
        if (banresponse != null && !banresponse.isEmpty())
            for (List<Object> row : banresponse) {
                if (row == null) return;
                try {
                    row.get(1);
                } catch (Exception e) {
                    continue;
                }
                try {
                    bannedPlayers.put(row.get(1), new BannedPlayer((String) row.get(1), (String) row.get(0), (String) row.get(2)));

                } catch (Exception e) {
                    bannedPlayers.put(row.get(1), new BannedPlayer((String) row.get(1), (String) row.get(0), "[unbekannt]"));

                }
            }

        SheetReader sr = new SheetReader(sheetid, "A3:D500", "SUSPENSIONS");
        List<List<Object>> response = sr.read();
        if (response != null && !response.isEmpty())
            for (List<Object> row : response) {
                if (row == null) return;
                try {
                    row.get(1);
                } catch (Exception e) {
                    continue;
                }
                suspendedPlayers.put(row.get(1), new SuspendedPlayer((String) row.get(1), (String) row.get(0), (String) row.get(2), (String) row.get(3)));
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
