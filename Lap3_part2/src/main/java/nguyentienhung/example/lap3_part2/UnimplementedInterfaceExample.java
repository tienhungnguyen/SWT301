package nguyentienhung.example.lap3_part2;
import java.util.logging.Logger;
interface Drawable {
    void draw();

}

class Circle implements Drawable {
    @Override
    public void draw() {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("Drawing a circle");
    }
}
