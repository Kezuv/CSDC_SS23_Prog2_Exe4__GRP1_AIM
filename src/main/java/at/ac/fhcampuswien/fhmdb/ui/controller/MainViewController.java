package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.models.User;
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
    public JFXButton homeBtn, watchListBtn, aboutBtn, loggoutBtn;
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

    private static boolean logedIn = false;

    private static User activeUser;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        checkIfLoggedIn();


        loggoutBtn.setText("Logout");

        HBox.setHgrow(setCenter, Priority.ALWAYS);
        setCenter.maxWidthProperty().bind(header.widthProperty().divide(2));

        HBox.setHgrow(setRight, Priority.ALWAYS);
        setRight.maxWidthProperty().bind(header.widthProperty());
        header.getChildren().clear();
        header.getChildren().addAll(fhmdbLogo, welcomeText,setCenter, homeBtn, watchListBtn, aboutBtn, setRight, userNameLabel, loggoutBtn);

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
            loggoutBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(watchListBtn)) {
            watchListBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            aboutBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggoutBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(aboutBtn)) {
            aboutBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggoutBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(loggoutBtn)) {
            loggoutBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            aboutBtn.getStyleClass().removeAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
        }

    }

    public void clickHomeBtn(ActionEvent actionEvent) throws IOException {
        if (logedIn) {
            changeBtnColors(homeBtn);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/home.fxml"));
            AnchorPane root = fxmlLoader.load();
            mainViewContent.setCenter(root);
        }
    }

    public void clickWatchListBtn(ActionEvent actionEvent) throws IOException {
        if (logedIn) {
            changeBtnColors(watchListBtn);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/watchlist.fxml"));
            AnchorPane root = fxmlLoader.load();
            mainViewContent.setCenter(root);
        }
    }

    public void clickAboutBtn(ActionEvent actionEvent) throws IOException {
        if (logedIn) {
            changeBtnColors(aboutBtn);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/about.fxml"));
            AnchorPane root = fxmlLoader.load();
            mainViewContent.setCenter(root);
        }
    }

    public void clickLogoutBtn(ActionEvent actionEvent) throws IOException {
        if (logedIn) {
            changeBtnColors(loggoutBtn);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/loginview.fxml"));
            AnchorPane view = fxmlLoader.load();
            mainViewContent.setCenter(view);
        }
    }
    private void checkIfLoggedIn(){
        if (!logedIn){
            userNameLabel.setText("");
            homeBtn.getStyleClass().addAll("background-light-black", "text-white");
            aboutBtn.getStyleClass().addAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().addAll("background-light-black", "text-white");
            loggoutBtn.getStyleClass().addAll("background-light-black", "text-white");
        } else {
            if (activeUser != null){
                userNameLabel.setText(activeUser.getUsername());
            }
            homeBtn.getStyleClass().addAll("background-yellow", "text-white");
            aboutBtn.getStyleClass().addAll("background-yellow", "text-white");
            watchListBtn.getStyleClass().addAll("background-yellow", "text-white");
            loggoutBtn.getStyleClass().addAll("background-yellow", "text-white");
        }
    }

    public static boolean isLogedIn() {
        return logedIn;
    }


    public static void setLogedIn(boolean logedIn) {
        MainViewController.logedIn = logedIn;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        MainViewController.activeUser = activeUser;
    }
}
