package dk.kea.kinoxp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public String formatDate(String date) throws ParseException {
        SimpleDateFormat ind = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ud = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = ind.parse(date);
        return ud.format(parsedDate);
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
    public void updateTicket(JSONObject updatedTicket) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(TICKET_FILE_PATH)));
        JSONObject json = new JSONObject(content);
        JSONArray tickets = json.getJSONArray("tickets");

        boolean ticketUpdated = false;
        for (int i = 0; i < tickets.length(); i++) {
            JSONObject ticket = tickets.getJSONObject(i);
            if (ticket.getString("ticketID").equals(updatedTicket.getString("ticketID"))) {
                // Update the fields in the existing ticket
                ticket.put("movieTitle", updatedTicket.getString("movieTitle"));
                ticket.put("showtime", updatedTicket.getString("showtime"));
                ticket.put("selectedSeats", updatedTicket.getJSONArray("selectedSeats"));
                ticket.put("totalPrice", updatedTicket.getString("totalPrice"));
                ticket.put("date", updatedTicket.getString("date"));
                ticket.put("extras", updatedTicket.getJSONObject("extras"));
                ticketUpdated = true;
                break;
            }
        }

        if (!ticketUpdated) {
            throw new RuntimeException("Ticket not found");
        }

        // Skriv tilbage til filen
        try (FileWriter file = new FileWriter(TICKET_FILE_PATH)) {
            file.write(json.toString(2));
        }
    }

    public void deleteTicket(String ticketId) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(TICKET_FILE_PATH)));
        JSONObject json = new JSONObject(content);
        JSONArray tickets = json.getJSONArray("tickets");

        boolean ticketDeleted = false;
        for (int i = 0; i < tickets.length(); i++) {
            JSONObject ticket = tickets.getJSONObject(i);
            if (ticket.getString("ticketID").equals(ticketId)) {
                tickets.remove(i); // Slet fra array
                ticketDeleted = true;
                break;
            }
        }

        if (!ticketDeleted) {
            throw new RuntimeException("Ticket not found");
        }

        try (FileWriter file = new FileWriter(TICKET_FILE_PATH)) {
            file.write(json.toString(2));
        }
    }
}
