package nguyentienhung.example.lap3_part2;

import java.util.logging.Logger;
import java.util.logging.Level;

public class OvercatchingExceptionExample {

    private static final Logger logger = Logger.getLogger(OvercatchingExceptionExample.class.getName());

    public static void main(String[] args) {
        try {
            int[] arr = new int[5];
            int index = 10;

            if (index >= 0 && index < arr.length) {
                logger.info("Value at index " + index + ": " + arr[index]);
            } else {
                logger.warning("Index " + index + " is out of bounds.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
        }
    }
}
