package at.ac.fhcampuswien.fhmdb.ui.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    public JFXButton homeBtn, watchListBtn, aboutBtn, loggingBtn;
    @FXML
    public Label userNameLabel, fhmdbLogo, welcomeText;
    @FXML
    public BorderPane mainViewContent;
    @FXML
    public AnchorPane contentView;
    @FXML
    public HBox header;
    private Region setRight = new Region();
    private Region setCenter = new Region();

    public void clickHomeBtn(ActionEvent actionEvent) throws IOException {
        changeBtnColors(homeBtn);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/home.fxml"));
        AnchorPane root = fxmlLoader.load();
        mainViewContent.setCenter(root);
    }

    public void clickWatchListBtn(ActionEvent actionEvent) throws IOException {
        changeBtnColors(watchListBtn);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/watchlist.fxml"));
        AnchorPane root = fxmlLoader.load();
        mainViewContent.setCenter(root);
    }

    public void clickAboutBtn(ActionEvent actionEvent) throws IOException {
        changeBtnColors(aboutBtn);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/about.fxml"));
        AnchorPane root = fxmlLoader.load();
        mainViewContent.setCenter(root);
    }

    public void clickLogOutBtn(ActionEvent actionEvent) throws IOException {
        changeBtnColors(loggingBtn);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/loginview.fxml"));
        AnchorPane view = fxmlLoader.load();
        mainViewContent.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //TODO add code / function to Change Login / Logout text in loggingBtn
        loggingBtn.setText("Logout / Login");

        HBox.setHgrow(setCenter, Priority.ALWAYS);
        setCenter.maxWidthProperty().bind(header.widthProperty().divide(2));

        HBox.setHgrow(setRight, Priority.ALWAYS);
        setRight.maxWidthProperty().bind(header.widthProperty());
        header.getChildren().clear();
        header.getChildren().addAll(fhmdbLogo, welcomeText,setCenter, homeBtn, watchListBtn, aboutBtn, setRight, userNameLabel, loggingBtn);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/loginview.fxml"));
        AnchorPane view = null;
        try {
            view = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainViewContent.setCenter(view);
    }

    //TODO reduce codereuse
    private void changeBtnColors(JFXButton pressedBtn){
        if (pressedBtn.equals(homeBtn)) {
            homeBtn.getStyleClass().addAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
            aboutBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggingBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(watchListBtn)) {
            watchListBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            aboutBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggingBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(aboutBtn)) {
            aboutBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggingBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(loggingBtn)) {
            loggingBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            aboutBtn.getStyleClass().removeAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
        }

    }
}
