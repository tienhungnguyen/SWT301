package nguyentienhung.example.lap3_part2;

enum Constants {
    MAX_USERS(100);  // Đây là enum constant

    private final int value;

    Constants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

public class InterfaceFieldModificationExample {
    public static void main(String[] args) {
        // Constants.MAX_USERS = 200; // Compile-time error
    }
}
