module nguyentienhung.example.lap3_part2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens nguyentienhung.example.lap3_part2 to javafx.fxml;
    exports nguyentienhung.example.lap3_part2;
    requires java.logging;
}