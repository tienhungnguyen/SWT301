package nguyentienhung.example.lap3_part2;

import java.util.logging.Logger;

public class NullPointerExample {
    private static final Logger logger = Logger.getLogger(NullPointerExample.class.getName());

    public static void main(String[] args) {
        // Nếu bạn không thực sự dùng biến `text`, hãy loại bỏ nó
        if ("string".isEmpty()) {
            logger.info("Text is not empty");
        }
    }
}
