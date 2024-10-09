package dk.kea.kinoxp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FragtmentController {
    private List<Shift> shifts = new ArrayList<>();
    @GetMapping("/fragments/header")
    public String getIndexHeader() {
        return "fragments/header";
    }

    @GetMapping("/fragments/cookies")
    public String getCookies() {
        return "fragments/cookies";
    }
    @GetMapping("/upcoming")
    public String showUpcoming() {
        return "upcoming";
    }
    @GetMapping("/cookiesshow")
    public String showCookiesInfo() {
        return "CookiesShow";
    }
    @GetMapping("/Omos")
    public String OmOs() {
        return "OmOs";
    }
    @GetMapping("/all")
    public List<Shift> getAllShifts() {
        return shifts;
    }

    @PostMapping("/add")
    public void addShift(@RequestBody Shift shift) {
        shifts.add(shift);

        System.out.println("Shift added: " + shift);
    }



}

