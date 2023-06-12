package at.ac.fhcampuswien.fhmdb.Exceptions;

// this class will handle all exceptions caused by the MovieAPI and the SearchParameter classes


import java.io.IOException;

public class MovieApiException extends IOException {
    public MovieApiException(String message) {
        super(message);
    }


    public static class PageOperationException extends RuntimeException {
        public PageOperationException(String message) {
            super("Web page open exception: " + message);
        }
    }

    public static class NotificationOperationException extends RuntimeException {
        public NotificationOperationException(String message) {
            super("Clear notifications exception: " + message);
        }
    }

    public static class ButtonOperationException extends IOException {
        private String customMessage;

        public ButtonOperationException(String message) {
            super();
            this.customMessage = message;
        }

        @Override
        public String getMessage() {
            return customMessage;
        }
    }


    public static String handleException(Exception e) {
        String message;
        if (e instanceof MovieApiException) {
            // Handle ApiException
            message = "ApiException occurred while making the API request: " + e.getMessage();
        } else if (e instanceof PageOperationException) {
            // Handle PageOperationException
            message = "PageOperationException occurred: " + e.getMessage();
        } else if (e instanceof NotificationOperationException) {
            // Handle NotificationOperationException
            message = "NotificationOperationException occurred: " + e.getMessage();
        } else if (e instanceof ButtonOperationException) {
            // Handle ButtonOperationException
            message = "ButtonOperationException occurred: " + e.getMessage();
        } else {
            // Handle other exceptions
            message = "General exception occurred: " + e.getMessage();
        }
        return message;
    }
}