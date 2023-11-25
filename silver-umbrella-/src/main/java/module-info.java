module com.example.silverumbrella {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens com.example.silverumbrella to javafx.fxml;
    exports com.example.silverumbrella;
    exports com.example.silverumbrella.controller;
    opens com.example.silverumbrella.controller to javafx.fxml;
}