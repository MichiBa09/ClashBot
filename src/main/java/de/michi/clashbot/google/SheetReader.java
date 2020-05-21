package de.michi.clashbot.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.util.List;

public class SheetReader {
    private String tableName;

    private String scope;

    private String sheetID;

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    private static final JsonFactory JSON_FACTORY = (JsonFactory)JacksonFactory.getDefaultInstance();

    public SheetReader(String sheetID, String scope, String tableName) {
        this.sheetID = sheetID;
        this.scope = scope;
        this.tableName = tableName;
    }

    public List<List<Object>> read() {
        try {
            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            String range = this.tableName + "!" + this.scope;
            Sheets service = (new Sheets.Builder((HttpTransport)HTTP_TRANSPORT, JSON_FACTORY, (HttpRequestInitializer)GoogleSheetsAuthentication.getCredentials(HTTP_TRANSPORT))).setApplicationName("Google Sheets API Java Quickstart").build();
            ValueRange response = (ValueRange)service.spreadsheets().values().get(this.sheetID, range).execute();
            List<List<Object>> values = response.getValues();
            return values;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
