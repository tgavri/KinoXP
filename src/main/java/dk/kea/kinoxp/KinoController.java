package dk.kea.kinoxp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class KinoController {

    private final DataHandler dataHandler = new DataHandler();

    @GetMapping("/CheckoutPage")
    public String showCheckout(Model model) {
        List<DataHandler.Items> items = dataHandler.getItems();
        model.addAttribute("items", items);
        return "Checkout";
    }

    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }

    @PostMapping("/Checkout")
    @ResponseBody
    public String saveTicket(
            @RequestParam("ticketID") String ticketID,
            @RequestParam("movieTitle") String movieTitle,
            @RequestParam("showtime") String showtime,
            @RequestParam("date") String date,
            @RequestParam("selectedSeats") String selectedSeats,
            @RequestParam("totalPrice") String totalPrice,
            @RequestParam("extras") String getExtras) throws JSONException, ParseException {

        String tempdate = dataHandler.formatDate(date);

        JSONObject ticketData = new JSONObject();
        ticketData.put("ticketID", ticketID);
        ticketData.put("movieTitle", movieTitle);
        ticketData.put("showtime", showtime);
        ticketData.put("date", tempdate);

        JSONArray seatsArray = new JSONArray(selectedSeats);
        ticketData.put("selectedSeats", seatsArray);

        ticketData.put("totalPrice", totalPrice);

        JSONObject extras = new JSONObject(getExtras);
        ticketData.put("extras", extras);

        dataHandler.saveTicket(ticketData);

        return "Ticket saved successfully!";
    }


    @GetMapping("/api/tickets")
    @ResponseBody
    public String getAllTickets() {
        JSONArray allTickets = dataHandler.getAllTickets();

        return allTickets.toString();
    }
    @GetMapping("/api/ticketsarray")
    @ResponseBody
    public String getAllTicketsArray() throws JSONException {
        JSONArray allTickets = dataHandler.getAllTickets();
        JSONObject response = new JSONObject();
        response.put("tickets", allTickets); // Wrap the array in an object
        return response.toString();
    }


    @PutMapping("/api/tickets/update")
    @ResponseBody
    public ResponseEntity<String> updateTicket(@RequestBody Map<String, Object> updatedTicket) {
        try {
            JSONObject jsonObject = new JSONObject(updatedTicket);
            dataHandler.updateTicket(jsonObject);
            return ResponseEntity.ok("Ticket updated successfully");
        } catch (Exception e) {
            e.printStackTrace(); // debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update the ticket: " + e.getMessage());
        }
    }


    @DeleteMapping("/api/tickets/delete")
    @ResponseBody
    public ResponseEntity<String> deleteTicket(@RequestBody Map<String, String> ticketToDelete) {
        try {
            String ticketId = ticketToDelete.get("ticketID");
            if (ticketId == null) {
                return ResponseEntity.badRequest().body("Ticket ID is required");
            }
            dataHandler.deleteTicket(ticketId);
            return ResponseEntity.ok("Ticket deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the ticket: " + e.getMessage());
        }
    }


    @GetMapping("/api/items")
        @ResponseBody
        public List<DataHandler.Items> getItems () {
            return dataHandler.getItems();
    }
}
