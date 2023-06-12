package at.ac.fhcampuswien.fhmdb.exceptions;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class DatabaseException {
    public static class ConnectionException extends Exception {
        public ConnectionException(String message) {
            super(message);
        }
    }

    public static class InitializationException extends Exception {
        public InitializationException(String message) {
            super(message);
        }
    }



    public static class MovieOperationException extends RuntimeException {
        public MovieOperationException(String message) {
                super("Add movie exception: " + message);
            }
    }

    public static class UserOperationException extends InvocationTargetException {
        private String customMessage;
        public UserOperationException(String message) {
            super();
            this.customMessage = message;
        }
        @Override
        public String getMessage() {
            return customMessage;
        }
    }



    public static void handleRepositoryException(Exception e) {
        if (e instanceof SQLException) {
            System.out.println("SQLException occurred in repository: " + e.getMessage());
        } else {
            System.out.println("Exception occurred in repository: " + e.getMessage());
        }
    }
}