package at.ac.fhcampuswien.fhmdb.ui.controller.factories;
import at.ac.fhcampuswien.fhmdb.ui.controller.WatchlistController;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

public class WatchlistControllerFactory implements Callback<Class<?>, Object> {

    private static WatchlistController instance;

    private static WatchlistController getInstance(){
        if (instance == null){
            instance = new WatchlistController();
        }
        return instance;
    }
    @Override
    public Object call(Class<?> aClass) {
        return getInstance();
    }
}
