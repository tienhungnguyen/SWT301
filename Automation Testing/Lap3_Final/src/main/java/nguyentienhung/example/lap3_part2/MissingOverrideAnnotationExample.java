package nguyentienhung.example.lap3_part2;
import java.util.logging.Logger;
class Animal {
    int speak() {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("Animal speaks");
        return 0;
    }
}

class Dog extends Animal {
    @Override
    public int speak() {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("Dog barks");
        return 0;
    }
}
