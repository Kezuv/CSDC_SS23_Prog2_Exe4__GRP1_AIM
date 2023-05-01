package at.ac.fhcampuswien.fhmdb.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    public void clickHomeBtn(ActionEvent actionEvent) {
    }

    public void clickWatchListBtn(ActionEvent actionEvent) {
    }

    public void clickAboutBtn(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void clickLogOutBtn(ActionEvent actionEvent) {
    }

    /*@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("content/mainview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 1000); //Size of the window raised to display all elements in the bottom bar
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles/styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();
    }*/
}
