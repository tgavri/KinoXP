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

    // Show the checkout page
    @GetMapping("/Checkout")
    public String showCheckout() {
        return "Checkout";
    }
    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }

    @PostMapping("/Checkout")
    @ResponseBody
    public String saveTicket(HttpServletRequest request) throws JSONException {

        String movieTitle = request.getParameter("movieTitle");
        String showtime = request.getParameter("showtime");
        String date = request.getParameter("date");
        String selectedSeats = request.getParameter("selectedSeats");
        String totalPrice = request.getParameter("totalPrice");
        String colaSmall = request.getParameter("colaSmall");
        String colaBig = request.getParameter("colaBig");
        String popcornSmall = request.getParameter("popcornSmall");
        String popcornBig = request.getParameter("popcornBig");

        JSONObject ticketData = new JSONObject();
        ticketData.put("ticketID", request.getParameter("ticketID"));
        ticketData.put("movieTitle", movieTitle);
        ticketData.put("showtime", showtime);
        ticketData.put("date", date);

        JSONArray seatsArray = new JSONArray(selectedSeats.split("\\n"));
        ticketData.put("selectedSeats", seatsArray);

        ticketData.put("totalPrice", totalPrice);

        JSONObject extras = new JSONObject();
        extras.put("colaSmall", colaSmall);
        extras.put("colaBig", colaBig);
        extras.put("popcornSmall", popcornSmall);
        extras.put("popcornBig", popcornBig);
        ticketData.put("extras", extras);

        dataHandler.saveTicket(ticketData);

        return "Ticket saved successfully!";
    }
}
