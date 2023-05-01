module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires com.google.gson;
    requires okhttp3;
    requires org.jsoup;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.api;
    exports at.ac.fhcampuswien.fhmdb.ui;
    opens at.ac.fhcampuswien.fhmdb.ui to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.ui.controller;
    opens at.ac.fhcampuswien.fhmdb.ui.controller to javafx.fxml;
}