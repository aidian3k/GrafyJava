module com.example.grafy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grafy to javafx.fxml;
    exports com.example.grafy;
}