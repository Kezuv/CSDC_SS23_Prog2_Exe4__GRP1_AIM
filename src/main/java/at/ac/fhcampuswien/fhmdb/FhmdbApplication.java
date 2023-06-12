package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.ui.controller.factories.ControllerFactory;
import at.ac.fhcampuswien.fhmdb.ui.controller.factories.Controllers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(ControllerFactory.getController(Controllers.MAIN_VIEW).load(), 1500, 1000); //Size of the window raised to display all elements in the bottom bar
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles/styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();

    }
}