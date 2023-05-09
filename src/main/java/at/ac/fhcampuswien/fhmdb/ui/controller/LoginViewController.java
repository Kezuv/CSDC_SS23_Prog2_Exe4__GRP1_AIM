package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.database.DatabaseUser;
import at.ac.fhcampuswien.fhmdb.database.UserEntity;
import at.ac.fhcampuswien.fhmdb.database.UserRepository;
import at.ac.fhcampuswien.fhmdb.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class LoginViewController {
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    public void clickLoginBtn(ActionEvent actionEvent) throws SQLException {
        // Get username and password from TextField
        String username = this.username.getText();
        String password = this.password.getText();
        User enteredUser = new User(username, password);
        /*UserEntity checkUser = UserRepository.getUserbyUsername(username);

        //Check if user already exists
        if(!(checkUser == null) && username.equals(checkUser.getUsername())){
            if(password.equals(checkUser.getPassword())){
                //Enables Watchlist, No new Login after registration, etc...
                System.out.println("Login erfolgreich!");
            }
            else{
                //No Login, but maybe new (Register)?
                System.out.println("Falsches Passwort!");
            }
        }else{//Add new user in database for the moment
            try {
                UserRepository.addToUsers(enteredUser);
                System.out.println("Neuer User!");
            } catch (SQLException e) {
                System.out.println("Fehler beim Hinzufügen des Benutzers zur Datenbank: " + e.getMessage());
            }
        }*/
    }

    public void clickRegisterBtn(ActionEvent actionEvent) throws IOException {
        // Get username and password from TextField
        String username = this.username.getText();
        String password = this.password.getText();

        // Create new User object
        User newUser = new User(username, password);

        // Add user to database
        try {
            UserRepository.addToUsers(newUser);
        } catch (SQLException e) {
            System.out.println("Fehler beim Hinzufügen des Benutzers zur Datenbank: " + e.getMessage());
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
