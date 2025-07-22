package nguyentienhung.example.lap3_part2;
import java.util.logging.Logger;
interface Shape {
    void draw();
    void resize();
}


class Square implements Shape {
    @Override
    public void draw() {
        Logger logger = Logger.getLogger(Square.class.getName());
        logger.info("Drawing square");
    }

    @Override
    public void resize() {
        Logger logger = Logger.getLogger(Square.class.getName());
        logger.info("Resizing square");
    }
}
