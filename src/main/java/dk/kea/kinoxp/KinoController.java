package dk.kea.kinoxp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            @RequestParam("extras") String getExtras) throws JSONException {

        JSONObject ticketData = new JSONObject();
        ticketData.put("ticketID", ticketID);
        ticketData.put("movieTitle", movieTitle);
        ticketData.put("showtime", showtime);
        ticketData.put("date", date);

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

    @GetMapping("/api/items")
    @ResponseBody
    public List<DataHandler.Items> getItems() {
        return dataHandler.getItems();
    }
}