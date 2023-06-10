package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.repos.UserRepository;
import at.ac.fhcampuswien.fhmdb.models.User;
import at.ac.fhcampuswien.fhmdb.ui.controller.factories.MainViewControllerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainViewControllerFactory mainViewControllerFactory = new MainViewControllerFactory();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("content/mainview.fxml"));
        fxmlLoader.setControllerFactory(mainViewControllerFactory);


        Scene scene = new Scene(fxmlLoader.load(), 1500, 1000); //Size of the window raised to display all elements in the bottom bar
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles/styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();

    }
}