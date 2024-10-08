package dk.kea.kinoxp;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final String DB_URL = "jdbc:mysql://mysql:3306/kino";
    private final String USER = "root";
    private final String PASSWORD = "mysqlpass";

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return "Login lykkedes!";
            } else {
                return "Forkert bruger eller kode.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Database fejl.";
        }
    }
}
