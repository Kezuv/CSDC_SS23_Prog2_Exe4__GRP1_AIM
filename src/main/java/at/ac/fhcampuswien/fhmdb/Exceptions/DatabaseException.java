package at.ac.fhcampuswien.fhmdb.Exceptions;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class DatabaseException {
    public static class ConnectionException extends Exception {
        public ConnectionException(String message) {
            super(message);
        }
    }

    public static class TableCreationException extends Exception {
        public TableCreationException(String message) {
            super(message);
        }
    }

    public static class DaoInitializationException extends Exception {
        public DaoInitializationException(String message) {
            super(message);
        }
    }


        public static class AddMovieException extends RuntimeException {
            public AddMovieException(String message) {
                super("Add movie exception: " + message);
            }
        }


        public static class GetMovieException extends RuntimeException {
            public GetMovieException(String message) {
                super("Get movie exception: " + message);
            }
        }

        public static class GetAllMoviesException extends RuntimeException {
            public GetAllMoviesException(String message) {
                super("Get all movies exception: " + message);
            }
        }

        public static class RegisterUserException extends RuntimeException {
            public RegisterUserException(String message, SQLException e) {
                super("Register user exception: " + message);
            }
        }

        public static class UserLoginException extends RuntimeException {
            public UserLoginException(String message) {
                super("User login exception: " + message);
            }
        }

        public static class UserExistsException extends InvocationTargetException {
            public UserExistsException(String message) {
                super();
            }
        }

        public static class GetMoviesForUserException extends RuntimeException {
            public GetMoviesForUserException(String message, Exception e) {
                super("Get movies for user exception: " + message);
            }
        }

        public static class AddMovieToWatchlistException extends RuntimeException {
            public AddMovieToWatchlistException(String message, SQLException e) {
                super("Add movie to watchlist exception: " + message);
            }
        }

        public static class RemoveMovieFromWatchlistException extends RuntimeException {
            public RemoveMovieFromWatchlistException(String message, SQLException e) {
                super("Remove movie from watchlist exception: " + message);
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
