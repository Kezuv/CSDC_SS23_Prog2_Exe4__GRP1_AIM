package at.ac.fhcampuswien.fhmdb.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginViewController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        // Set up functionality for login and register buttons
        loginButton.setOnAction(event -> {
            // Add code to handle login button click
        });

        registerButton.setOnAction(event -> {
            // Add code to handle register button click
        });
    }
}
