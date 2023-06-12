package at.ac.fhcampuswien.fhmdb.exceptions;

// this class will handle all exceptions caused by the MovieAPI and the SearchParameter classes


import java.io.IOException;
import java.sql.SQLException;

public class MovieApiException extends IOException {
    public MovieApiException(String message) {
        super(message);
    }

    public static String handleException(Exception e){
        if (e instanceof IOException) {
            return "An IO exception occurred while making the API request: " + e.getMessage();
        } else {
            return "An unexpected exception occurred: " + e.getMessage();
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

    public static class HomeButtonException extends IOException {
        private String customMessage;
        public HomeButtonException(String message) {
            super();
            this.customMessage = message;
        }
        @Override
        public String getMessage() {
            return customMessage;
        }
    }

    public static class WatchListButtonException extends RuntimeException {
        public WatchListButtonException(String message) {
            super(message);
        }
    }

    public static class LogoutButtonException extends RuntimeException {
        public LogoutButtonException(String message) {
            super(message);
        }
    }

    public static String handleHomeControllerException(Exception e) {
        // Handle exceptions specific to HomeController
        String message;
        if (e instanceof IOException) {
            // Handle IOException
           message = "IOException occurred in HomeController: " + e.getMessage();
        } else if (e instanceof SQLException) {
            // Handle SQLException
            message = "SQLException occurred in HomeController: " + e.getMessage();
        } else {
            // Handle other exceptions
            message = "Exception occurred in HomeController: " + e.getMessage();
        }
        return message;
    }
}