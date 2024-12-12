module org.example.javalabweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires static lombok;
    requires java.sql;
    requires org.slf4j;

    opens org.example.javalabweatherapp to javafx.fxml;
    exports org.example.javalabweatherapp;
}