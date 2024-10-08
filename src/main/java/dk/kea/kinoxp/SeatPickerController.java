package dk.kea.kinoxp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeatPickerController {
    @GetMapping("/SeatPickerSmall")
    public String showSeatPickerSmall() {
        return "SeatPickerSmall";
    }
    @GetMapping("/SeatPickerBig")
    public String showSeatPickerBig() {
        return "SeatPickerBig";
    }
    @GetMapping("/SeatPickerEkstra")
    public String showSeatPickerEkstra() {
        return "SeatPickerEkstra";
    }
}
