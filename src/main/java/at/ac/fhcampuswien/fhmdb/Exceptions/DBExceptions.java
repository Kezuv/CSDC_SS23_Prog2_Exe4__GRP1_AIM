package at.ac.fhcampuswien.fhmdb.Exceptions;

public class DBExceptions {
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
}
