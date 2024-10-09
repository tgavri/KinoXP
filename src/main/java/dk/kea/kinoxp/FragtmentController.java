package dk.kea.kinoxp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragtmentController {
    @GetMapping("/fragments/header")
    public String getIndexHeader() {
        return "fragments/header";
    }

    @GetMapping("/fragments/footer")
    public String getIndexFooter() {
        return "fragments/footer";
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
    @GetMapping("/adminpanel")
    public String showAdminPanel() {
        return "adminpanel"; // This assumes you have a worker.html file in the templates folder
    }
}

