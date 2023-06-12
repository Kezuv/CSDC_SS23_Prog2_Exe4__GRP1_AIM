package at.ac.fhcampuswien.fhmdb.ui.controller.factories;
import javafx.fxml.FXMLLoader;

public class ControllerFactory {

    public static FXMLLoader getController(Controllers controller){
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerFactory.class.getResource(controller.getPath()));

        switch (controller){
            case MAIN_VIEW:
                fxmlLoader.setControllerFactory(new MainViewControllerFactory());
                break;

            case LOGIN_VIEW:
                fxmlLoader.setControllerFactory(new LoginViewControllerFactory());
                break;

            case HOME:
                fxmlLoader.setControllerFactory(new HomeControllerFactory());
                break;

            case WATCHLIST:
                fxmlLoader.setControllerFactory(new WatchlistControllerFactory());
                break;
        }

        return fxmlLoader;
    }

}
