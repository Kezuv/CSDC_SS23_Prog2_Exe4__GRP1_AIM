package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.User;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    public JFXButton homeBtn, watchListBtn, loggoutBtn;
    @FXML
    public Label userNameLabel, fhmdbLogo, welcomeText, homeError;
    @FXML
    public BorderPane mainViewContent;
    @FXML
    public AnchorPane contentView;
    @FXML
    public HBox header;
    private Region setRight = new Region();
    private Region setCenter = new Region();

    private static BooleanProperty loggedInProperty = new SimpleBooleanProperty(false);

    private static User activeUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedInProperty.addListener((observable, oldValue, newValue) -> {
            checkIfLoggedIn();
        });
        checkIfLoggedIn();
        loggoutBtn.setText("Logout");

        HBox.setHgrow(setCenter, Priority.ALWAYS);
        setCenter.maxWidthProperty().bind(header.widthProperty().divide(2));

        HBox.setHgrow(setRight, Priority.ALWAYS);
        setRight.maxWidthProperty().bind(header.widthProperty());
        header.getChildren().clear();
        header.getChildren().addAll(fhmdbLogo, welcomeText,setCenter, homeBtn, watchListBtn, setRight, userNameLabel, loggoutBtn);
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/loginview.fxml"));

        AnchorPane view = null;
        view = fxmlLoader.load();
        mainViewContent.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException re){
            try {
                throw new MovieApiException.HomeButtonException("can't reach API");
            } catch (MovieApiException.HomeButtonException e) {
                homeError.getStyleClass().add("text-red");
                homeError.setText(e.getMessage());
            }
        }
    }

    private void changeBtnColors(JFXButton pressedBtn){
        if (pressedBtn.equals(homeBtn)) {
            homeBtn.getStyleClass().addAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggoutBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(watchListBtn)) {
            watchListBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            loggoutBtn.getStyleClass().removeAll("background-light-black", "text-white");
        } else if (pressedBtn.equals(loggoutBtn)) {
            loggoutBtn.getStyleClass().addAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
        }
    }

    public void clickHomeBtn(ActionEvent actionEvent) {
        if (isLogedIn()) {
            try {
                changeBtnColors(homeBtn);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/home.fxml"));
                AnchorPane root = fxmlLoader.load();
                mainViewContent.setCenter(root);
            } catch (MovieApiException.HomeButtonException e) {
                homeError.getStyleClass().add("text-red");
                homeError.setText(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clickWatchListBtn(ActionEvent actionEvent) throws IOException {
        if (isLogedIn()) {
            try {
                changeBtnColors(watchListBtn);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/watchlist.fxml"));
                AnchorPane root = fxmlLoader.load();
                mainViewContent.setCenter(root);
            } catch (IOException e) {
                System.out.println("Error loading watchlist.fxml: " + e.getMessage());
                throw new MovieApiException.WatchListButtonException("Error loading watchlist page: " + e.getMessage());
            }
        }
    }

    public void clickLogoutBtn(ActionEvent actionEvent) throws IOException {
        if (isLogedIn()) {
            try {
                loggedInProperty.set(false);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/at/ac/fhcampuswien/fhmdb/content/loginview.fxml"));
                AnchorPane view = fxmlLoader.load();
                mainViewContent.setCenter(view);
            } catch (IOException e) {
                System.out.println("Error loading loginview.fxml: " + e.getMessage());
                throw new MovieApiException.LogoutButtonException("Error loading login page: " + e.getMessage());
            }
        }
    }

    private void checkIfLoggedIn(){
        if (!isLogedIn()){
            userNameLabel.setText("");
            homeBtn.getStyleClass().addAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().addAll("background-light-black", "text-white");
            loggoutBtn.getStyleClass().addAll("background-light-black", "text-white");
        } else {
            if (activeUser != null){
                userNameLabel.setText(activeUser.getUsername());
            }
            loggoutBtn.getStyleClass().removeAll("background-light-black", "text-white");
            homeBtn.getStyleClass().removeAll("background-light-black", "text-white");
            watchListBtn.getStyleClass().removeAll("background-light-black", "text-white");
        }
    }

    public static boolean isLogedIn() {
        return loggedInProperty.get();
    }

    public static void setLogedIn(boolean logedIn) {
        loggedInProperty.set(logedIn);
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        MainViewController.activeUser = activeUser;
    }
}
