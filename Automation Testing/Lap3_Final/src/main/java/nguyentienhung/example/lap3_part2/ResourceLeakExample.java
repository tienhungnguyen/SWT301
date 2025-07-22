package nguyentienhung.example.lap3_part2;

import java.util.logging.Logger;
import java.io.*;

public class ResourceLeakExample {

    private static final Logger logger = Logger.getLogger(ResourceLeakExample.class.getName());

    public static void main(String[] args) {
        // Sử dụng try-with-resources để đảm bảo tự động đóng tài nguyên
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
        }
    }
}
