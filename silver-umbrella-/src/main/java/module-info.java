module com.example.silverumbrella {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.silverumbrella to javafx.fxml;
    exports com.example.silverumbrella;
    exports com.example.silverumbrella.controller;
    opens com.example.silverumbrella.controller to javafx.fxml;
}