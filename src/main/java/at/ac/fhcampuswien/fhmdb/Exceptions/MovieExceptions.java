package at.ac.fhcampuswien.fhmdb.Exceptions;

// this exception class will handle all exceptions caused by Genre, Movie and SortedState classes


public class MovieExceptions {

    public static class InvalidSearchException extends Exception {
        public InvalidSearchException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidSortingException extends Exception {
        public InvalidSortingException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidYearException extends Exception {
        public InvalidYearException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidRatingException extends Exception {
        public InvalidRatingException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class MovieNotFoundException extends Exception {
        public MovieNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidMovieDataException extends Exception {
        public InvalidMovieDataException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidURLException extends Exception {
        public InvalidURLException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidSearchParameterException extends Exception {
        public InvalidSearchParameterException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class InvalidSortedStateException extends Exception {
        public InvalidSortedStateException(String message) {
            super(message);
        }
    }

}
