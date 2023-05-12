package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.repos.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class LoginViewController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label loginError, registerSuccess;

    public void clickLoginBtn(ActionEvent actionEvent){
        if (usernameField.getText() != null && !usernameField.getText().equals("") &&
                passwordField.getText() != null && !passwordField.getText().equals("")){
            clearNotifications();
            try {
                MainViewController.setActiveUser(UserRepository.userLogIn(usernameField.getText(), passwordField.getText()));
                MainViewController.setLogedIn(true);
                loginError.getStyleClass().add("text-green");
                loginError.setText("Login succeed!");
                System.out.println("Login succeed!");
            } catch (SQLException e) {
                //TODO ALLERT when Username or Password is false - Exception?
                System.out.println("Username or Password false");
                throw new RuntimeException(e);
            } catch (IndexOutOfBoundsException e){
                loginError.getStyleClass().add("text-red");
                loginError.setText("Username or password wrong. Please try again.");
            }
        }

    }

    public void clickRegisterBtn(ActionEvent actionEvent) {
        if (usernameField.getText() != null && !usernameField.getText().equals("") &&
                passwordField.getText() != null && !passwordField.getText().equals("")){
            clearNotifications();
            try {
                UserRepository.registerUser(usernameField.getText(), passwordField.getText());
                System.out.println("Register complete!");
                registerSuccess.getStyleClass().add("text-green");
                registerSuccess.setText("Register complete! Please Log In.");
                usernameField.clear();
                passwordField.clear();
                loginError.setStyle("");

            } catch (SQLException e) {
                registerSuccess.getStyleClass().add("text-red");
                registerSuccess.setText("Register failed!");
                System.out.println("Register failed - user exist?");
                //TODO ALERT USER EXIST - EXCEPTION ?
                throw new RuntimeException(e);
            }
        }
    }

    public void openMoreInfo(MouseEvent mouseEvent) {
        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println("Could not open webpage: " + url);
        }
    }

    private void clearNotifications(){
        loginError.setText("");
        loginError.getStyleClass().clear();
        registerSuccess.setText("");
        registerSuccess.getStyleClass().clear();
    }
}
