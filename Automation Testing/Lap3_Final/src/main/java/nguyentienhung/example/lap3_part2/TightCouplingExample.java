package nguyentienhung.example.lap3_part2;
import java.util.logging.Logger;
class Printer {
    void print(String message) {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info(message);
    }
}

class Report {
    private Printer printer = new Printer(); // tightly coupled

    void generate() {
        printer.print("Generating report...");
    }
}
