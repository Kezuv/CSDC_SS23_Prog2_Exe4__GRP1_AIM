package at.ac.fhcampuswien.fhmdb.ui.controller.factories;

import at.ac.fhcampuswien.fhmdb.ui.controller.LoginViewController;
import javafx.util.Callback;

public class LoginViewControllerFactory implements Callback<Class<?>, Object> {

    private static LoginViewController instance;

    private LoginViewController getInstance(){
        if (instance == null){
            instance = new LoginViewController();
        } return instance;
    }


    @Override
    public Object call(Class<?> aClass) {
        return getInstance();
    }
}
