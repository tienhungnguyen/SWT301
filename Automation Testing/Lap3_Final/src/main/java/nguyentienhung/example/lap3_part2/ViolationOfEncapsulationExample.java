package nguyentienhung.example.lap3_part2;

import java.util.logging.Level;
import java.util.logging.Logger;

class User {

    public static final int SOME_CONSTANT = 0;

    private String name;
    private int age;
    public String getFirstName() {
        return name;
    }
    public void setFirstName(String firstName) {
        this.name = firstName;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void display() {
        Logger logger = Logger.getLogger(User.class.getName());
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Name: %s Age: %d", name, age));
        }
    }

}
