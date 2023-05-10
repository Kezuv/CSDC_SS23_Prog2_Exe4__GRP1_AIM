package at.ac.fhcampuswien.fhmdb.Exceptions;

// this class will handle all exceptions caused by the MovieAPI and the SearchParameter classes


import java.io.IOException;

public class APIExceptions extends IOException {
    public APIExceptions(String message) {
        super(message);
    }

    public static String handleException(Exception e){
        if (e instanceof IOException) {
            return "An IO exception occurred while making the API request: " + e.getMessage();
        } else {
            return "An unexpected exception occurred: " + e.getMessage();
        }
    }
}