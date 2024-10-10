package dk.kea.kinoxp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    private static final String TICKET_FILE_PATH = "/app/src/main/resources/static/tickets.json";


    private final List<Items> items = new ArrayList<>();

    public DataHandler() {
        items.add(new Items("Lille Popcorn", 20));
        items.add(new Items("Stor Popcorn", 35));
        items.add(new Items("Lille Cola", 20));
        items.add(new Items("Stor Cola", 30));
        items.add(new Items("Haribo Clickmix", 69));
    }

    public List<Items> getItems() {
        return items;
    }

    public static class Items {   // MIDLERTIDIG TESTKLASSE
        private final String name;
        private final int price;

        public Items(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

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
    public void updateTicket(JSONObject updatedTicket) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(TICKET_FILE_PATH)));
            JSONObject json = new JSONObject(content);
            JSONArray tickets = json.getJSONArray("tickets");

            boolean ticketUpdated = false;
            for (int i = 0; i < tickets.length(); i++) {
                JSONObject ticket = tickets.getJSONObject(i);
                if (ticket.getString("ticketID").equals(updatedTicket.getString("ticketID"))) {
                    tickets.put(i, updatedTicket); // Replace old ticket with the updated one
                    ticketUpdated = true;
                    break;
                }
            }

            if (!ticketUpdated) {
                throw new RuntimeException("Ticket not found");
            }

            // Write updated tickets back to the file
            try (FileWriter file = new FileWriter(TICKET_FILE_PATH)) {
                file.write(json.toString(2));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void deleteTicket(JSONObject ticketToDelete) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(TICKET_FILE_PATH)));
            JSONObject json = new JSONObject(content);
            JSONArray tickets = json.getJSONArray("tickets");

            boolean ticketDeleted = false;
            for (int i = 0; i < tickets.length(); i++) {
                JSONObject ticket = tickets.getJSONObject(i);
                if (ticket.getString("ticketID").equals(ticketToDelete.getString("ticketID"))) {
                    tickets.remove(i); // Remove the ticket from the array
                    ticketDeleted = true;
                    break;
                }
            }

            if (!ticketDeleted) {
                throw new RuntimeException("Ticket not found");
            }

            // Write updated tickets back to the file
            try (FileWriter file = new FileWriter(TICKET_FILE_PATH)) {
                file.write(json.toString(2));
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO Exception occurred: " + e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("JSON Exception occurred: " + e.getMessage());
        }
    }
}
