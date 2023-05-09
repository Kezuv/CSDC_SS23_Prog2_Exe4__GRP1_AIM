package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.repos.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class LoginViewController {
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;
    public void clickLoginBtn(ActionEvent actionEvent){
        if (usernameField.getText() != null && !usernameField.getText().equals("") &&
                passwordField.getText() != null && !passwordField.getText().equals("")){

            try {
                MainViewController.setActiveUser(UserRepository.userLogIn(usernameField.getText(), passwordField.getText()));
                MainViewController.setLogedIn(true);
                System.out.println("login succeed!");
            } catch (SQLException e) {
                //TODO ALLERT when Username or Password is false - Exception?
                System.out.println("Username or Password false");
                throw new RuntimeException(e);
            }
        }

    }

    public void clickRegisterBtn(ActionEvent actionEvent) {
        if (usernameField.getText() != null && !usernameField.getText().equals("") &&
                passwordField.getText() != null && !passwordField.getText().equals("")){
            try {
                UserRepository.registerUser(usernameField.getText(), passwordField.getText());
                System.out.println("Register complete!");
            } catch (SQLException e) {
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
}
