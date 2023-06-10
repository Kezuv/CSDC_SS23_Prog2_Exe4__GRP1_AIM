package at.ac.fhcampuswien.fhmdb.ui.controller.factories;

import at.ac.fhcampuswien.fhmdb.ui.controller.MainViewController;
import javafx.util.Callback;

public class MainViewControllerFactory implements Callback<Class<?>, Object> {

    private static MainViewController instance;

    private MainViewController getInstance(){
        if (instance == null){
            instance = new MainViewController();
        }
        return instance;
    }
    @Override
    public Object call(Class<?> aClass) {
        return getInstance();
    }
}
