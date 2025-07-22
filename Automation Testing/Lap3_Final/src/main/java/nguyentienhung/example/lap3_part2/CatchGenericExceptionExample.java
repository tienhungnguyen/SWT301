package nguyentienhung.example.lap3_part2;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CatchGenericExceptionExample {

    private static final Logger logger = Logger.getLogger(CatchGenericExceptionExample.class.getName());

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            logger.info("Enter a string: ");
            String s = scanner.nextLine();

            if (s != null && logger.isLoggable(Level.INFO)) {
                logger.info(String.format("Length: %d", s.length()));
            } else {
                logger.warning("Input is null, cannot compute length.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
        }
    }
}
