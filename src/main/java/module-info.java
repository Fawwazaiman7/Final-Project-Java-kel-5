module BeritaMedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires org.json;
    requires java.desktop;
    requires javafx.web;
    requires javafx.swing;
    requires java.sql;
    requires jogl.all;


    opens com.beritamedia.app to javafx.fxml;
    opens com.beritamedia.app.controller to javafx.fxml;  // Tambahkan ini
    opens com.beritamedia.app.model to com.fasterxml.jackson.databind;

    exports com.beritamedia.app;
    exports com.beritamedia.app.model; // Tambahkan ini untuk mengekspor paket model
    exports com.beritamedia.app.controller;  // Tambahkan ini
}