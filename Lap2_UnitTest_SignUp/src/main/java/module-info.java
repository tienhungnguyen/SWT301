module tienhung.service.lap2_unittest_signup {
    requires javafx.controls;
    requires javafx.fxml;


    opens tienhung.service.service to javafx.fxml;
    exports tienhung.service.service;
}