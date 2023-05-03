package at.ac.fhcampuswien.fhmdb.ui.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class LoginViewController {
    public void clickLoginBtn(ActionEvent actionEvent) {
    }

    public void clickRegisterBtn(ActionEvent actionEvent) {
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
