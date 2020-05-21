package de.michi.clashbot.cocapi.request;

import de.michi.clashbot.ClashBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SupercellRequest {

    public String tag;
    public RequestType type;

    public SupercellRequest(String tag, RequestType type) {
        this.tag = tag.replace("#", "%23");
        this.type = type;
    }

    public String execute() {
        String url = "https://api.clashofclans.com/v1/";
        switch (this.type) {
            case CLAN:
                url = url + "clans/" + this.tag;
                break;
            case CURRENTWAR:
                url = url + "clans/" + this.tag + "/currentwar";
                break;
            case PLAYER:
                url = url + "players/" + this.tag;
                break;
            case WARLOG:
                url = url + "clans/" + this.tag + "/warlog";
                break;
            case LEAGUEGROUP:
                url = url + "clans/" + this.tag + "/currentwar/leaguegroup";
        }
        StringBuffer response = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + ClashBot.supercellKey);
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                response = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                in.close();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
