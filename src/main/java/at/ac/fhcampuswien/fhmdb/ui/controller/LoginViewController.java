package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.repos.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;

public class LoginViewController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label loginError, registerSuccess;

    public void clickLoginBtn(ActionEvent actionEvent) {
        if (usernameField.getText() != null && !usernameField.getText().equals("") &&
                passwordField.getText() != null && !passwordField.getText().equals("")) {
            clearNotifications();
            try {
                MainViewController.setActiveUser(UserRepository.userLogIn(usernameField.getText(), passwordField.getText()));
                MainViewController.setLogedIn(true);
                loginError.getStyleClass().add("text-green");
                loginError.setText("Login succeed!");
                System.out.println("Login succeed!");
            } catch ( DatabaseException.UserLoginException ule) {
                loginError.getStyleClass().add("text-red");
                loginError.setText("Login failed: "+ ule.getMessage());
            }
        }

    }

    public void clickRegisterBtn(ActionEvent actionEvent) {
        try {
            if (usernameField.getText() != null && !usernameField.getText().equals("") &&
                    passwordField.getText() != null && !passwordField.getText().equals("")) {
                clearNotifications();
                String username = usernameField.getText();
                if (UserRepository.isUserExists(username)) {
                    throw new DatabaseException.UserExistsException("Username <" + username + "> already exists" );
                }
                UserRepository.registerUser(username, passwordField.getText());
                System.out.println("Register complete!");
                registerSuccess.getStyleClass().add("text-green");
                registerSuccess.setText("Register complete! Please Log In.");
                usernameField.clear();
                passwordField.clear();
                loginError.setStyle("");
            }
        } catch (DatabaseException.UserExistsException e) {
            registerSuccess.getStyleClass().add("text-red");
            registerSuccess.setText("Register failed! " + e.getMessage());
        } catch (DatabaseException.RegisterUserException re){
            registerSuccess.setText("Register failed! " + re.getMessage());
        }
    }

    public void openMoreInfo(MouseEvent mouseEvent) {
        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println("Could not open webpage: " + url);
            throw new MovieApiException.WebPageOpenException("Error opening webpage: " + e.getMessage());
        }
    }

    private void clearNotifications() {
        try {
            loginError.setText("");
            loginError.getStyleClass().clear();
            registerSuccess.setText("");
            registerSuccess.getStyleClass().clear();
        } catch (Exception e) {
            throw new MovieApiException.ClearNotificationsException("Error clearing notifications: " + e.getMessage());
        }
    }
}