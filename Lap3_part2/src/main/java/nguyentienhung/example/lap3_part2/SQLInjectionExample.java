package nguyentienhung.example.lap3_part2;

import java.util.logging.Logger;
import java.util.logging.Level;

public class SQLInjectionExample {
    private static final Logger logger = Logger.getLogger(SQLInjectionExample.class.getName());

    public static void main(String[] args) {
        String userInput = "' OR '1'='1";
        String query = String.format("SELECT * FROM users WHERE username = '%s'", userInput);

        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Executing query: %s", query));
        }
    }
}
