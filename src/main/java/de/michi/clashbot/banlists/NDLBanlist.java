package de.michi.clashbot.banlists;

import de.michi.clashbot.google.SheetReader;

import java.util.HashMap;
import java.util.List;

public class NDLBanlist {


    private String sheetid;
    public HashMap bannedPlayers = new HashMap<String, BannedPlayer>();

    public NDLBanlist() {
        this.sheetid = "1UPW6YoTffseUGalDlnLlsK9NxvI0RkuwmPaiWTMotUs";
    }


    public void update() {
        SheetReader br = new SheetReader(sheetid, "A3:E1000", "Account Bans");
        List<List<Object>> banresponse = br.read();
        if (banresponse != null && !banresponse.isEmpty())
            for (List<Object> row : banresponse) {
                if (row == null) return;
                try {
                    row.get(0);
                    row.get(1);
                } catch (Exception e) {
                    continue;
                }
                try {
                    bannedPlayers.put(row.get(0), new BannedPlayer((String) row.get(0), (String) row.get(1), (String) row.get(4)));
                } catch (Exception e) {
                    bannedPlayers.put(row.get(0), new BannedPlayer((String) row.get(0), (String) row.get(1), "[unbekannt]"));
                }
            }
    }

    public boolean checkBan(String tag) {
        return this.bannedPlayers.containsKey(tag);
    }

    public BannedPlayer getBannedPlayer(String tag) {
        return (BannedPlayer) this.bannedPlayers.get(tag);
    }
}