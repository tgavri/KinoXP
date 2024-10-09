package dk.kea.kinoxp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class KinoController {

    private final DataHandler dataHandler = new DataHandler();

    @GetMapping("/CheckoutPage")
    public String showCheckout() {
        return "Checkout";
    }
    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }

    @GetMapping("/Checkout")
    @ResponseBody
    public String saveTicket(
            @RequestParam("ticketID") String ticketID,
            @RequestParam("movieTitle") String movieTitle,
            @RequestParam("showtime") String showtime,
            @RequestParam("date") String date,
            @RequestParam("selectedSeats") String selectedSeats,
            @RequestParam("totalPrice") String totalPrice,
            @RequestParam(value = "colaSmall", required = false) String colaSmall,
            @RequestParam(value = "colaBig", required = false) String colaBig,
            @RequestParam(value = "popcornSmall", required = false) String popcornSmall,
            @RequestParam(value = "popcornBig", required = false) String popcornBig) throws JSONException {

        // Create a JSONObject to hold the ticket data
        JSONObject ticketData = new JSONObject();
        ticketData.put("ticketID", ticketID);
        ticketData.put("movieTitle", movieTitle);
        ticketData.put("showtime", showtime);
        ticketData.put("date", date);

        // Convert selectedSeats into a JSONArray
        JSONArray seatsArray = new JSONArray(selectedSeats.split("\\n"));
        ticketData.put("selectedSeats", seatsArray);

        ticketData.put("totalPrice", totalPrice);

        // Add extras (cola and popcorn) to the JSON object
        JSONObject extras = new JSONObject();
        extras.put("colaSmall", colaSmall != null ? colaSmall : "");
        extras.put("colaBig", colaBig != null ? colaBig : "");
        extras.put("popcornSmall", popcornSmall != null ? popcornSmall : "");
        extras.put("popcornBig", popcornBig != null ? popcornBig : "");
        ticketData.put("extras", extras);

        // Save the ticket using the DataHandler
        dataHandler.saveTicket(ticketData);

        return "Ticket saved successfully!";
    }

    @GetMapping("/api/tickets")
    @ResponseBody
    public String getAllTickets() {
        JSONArray allTickets = dataHandler.getAllTickets();

        return allTickets.toString();
    }
}