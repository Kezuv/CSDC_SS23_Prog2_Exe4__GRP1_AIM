package at.ac.fhcampuswien.fhmdb.ui.controller.factories;

import at.ac.fhcampuswien.fhmdb.ui.controller.HomeController;
import javafx.util.Callback;

public class HomeControllerFactory implements Callback<Class<?>, Object> {

    private static HomeController instance;


    private static HomeController getInstance(){
        if (instance == null){
            instance = new HomeController();
        }
        return instance;
    }
    @Override
    public Object call(Class<?> aClass) {
        return getInstance();
    }
}
