package dk.kea.kinoxp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataHandler {

    private static final String TICKET_FILE_PATH = "/app/src/main/resources/static/tickets.json";


    public void saveTicket(JSONObject newTicket) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(TICKET_FILE_PATH)));
            JSONObject json = new JSONObject(content);
            JSONArray tickets = json.getJSONArray("tickets");

            tickets.put(newTicket);

            try (FileWriter file = new FileWriter(TICKET_FILE_PATH)) {
                file.write(json.toString(2));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray getAllTickets() { // hent alle biletter (hvis det skal bruges i en javaklasse)
        JSONArray tickets = new JSONArray();

        try {
            String content = new String(Files.readAllBytes(Paths.get(TICKET_FILE_PATH)));
            JSONObject json = new JSONObject(content);

            tickets = json.getJSONArray("tickets");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }
}
