module com.example.message_client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.message_client to javafx.fxml;
    exports com.example.message_client;
}