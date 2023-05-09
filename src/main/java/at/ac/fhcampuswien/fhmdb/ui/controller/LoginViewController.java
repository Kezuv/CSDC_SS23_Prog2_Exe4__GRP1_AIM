package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.repos.UserRepository;
import at.ac.fhcampuswien.fhmdb.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class LoginViewController {
    @FXML
    public TextField usernameField;
    @FXML
    public TextField passwordField;
    public void clickLoginBtn(ActionEvent actionEvent) throws SQLException {
        // Get username and password from TextField
        new User(usernameField.getText(), passwordField.getText());


    }

    public void clickRegisterBtn(ActionEvent actionEvent) throws IOException {

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
