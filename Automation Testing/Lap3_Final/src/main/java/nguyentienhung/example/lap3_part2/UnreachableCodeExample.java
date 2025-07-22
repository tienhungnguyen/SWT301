package nguyentienhung.example.lap3_part2;

import java.util.logging.Logger;

public class UnreachableCodeExample {

    public static int getNumber() {
        Logger logger = Logger.getLogger(UnreachableCodeExample.class.getName());
        logger.info("This will never execute"); // Thực ra dòng này sẽ chạy
        return 42;
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(UnreachableCodeExample.class.getName());
        logger.info(String.valueOf(getNumber())); // hoặc: logger.info("Value: " + getNumber());
    }
}
