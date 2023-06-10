package at.ac.fhcampuswien.fhmdb.ui.controller.factories;

import javafx.util.Callback;

public class MainViewControllerFactory implements Callback<Class<?>, Object> {

    private static MainViewControllerFactory instance;

    private MainViewControllerFactory getInstance(){
        if (instance == null){
            instance = new MainViewControllerFactory();
        }
        return instance;
    }
    @Override
    public Object call(Class<?> aClass) {
        return getInstance();
    }
}
