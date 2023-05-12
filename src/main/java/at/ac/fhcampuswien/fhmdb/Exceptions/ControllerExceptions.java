package at.ac.fhcampuswien.fhmdb.Exceptions;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerExceptions {

    public static class RegistrationException extends RuntimeException {
        public RegistrationException(String message) {
            super("Registration exception: " + message);
        }
    }

    public static class WebPageOpenException extends RuntimeException {
        public WebPageOpenException(String message) {
            super("Web page open exception: " + message);
        }
    }

    public static class ClearNotificationsException extends RuntimeException {
        public ClearNotificationsException(String message) {
            super("Clear notifications exception: " + message);
        }
    }

    public static class HomeButtonException extends RuntimeException {
        public HomeButtonException(String message) {
            super(message);
        }
    }

    public static class WatchListButtonException extends RuntimeException {
        public WatchListButtonException(String message) {
            super(message);
        }
    }

    public static class AboutButtonException extends RuntimeException {
        public AboutButtonException(String message) {
            super(message);
        }
    }

    public static class LogoutButtonException extends RuntimeException {
        public LogoutButtonException(String message) {
            super(message);
        }
    }

    public static void handleHomeControllerException(Exception e) {
        // Handle exceptions specific to HomeController
        if (e instanceof IOException) {
            // Handle IOException
            System.out.println("IOException occurred in HomeController: " + e.getMessage());
        } else if (e instanceof SQLException) {
            // Handle SQLException
            System.out.println("SQLException occurred in HomeController: " + e.getMessage());
        } else {
            // Handle other exceptions
            System.out.println("Exception occurred in HomeController: " + e.getMessage());
        }
    }
}
