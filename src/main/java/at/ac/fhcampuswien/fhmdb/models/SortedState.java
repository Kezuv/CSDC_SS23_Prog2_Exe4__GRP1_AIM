package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieExceptions;

public enum SortedState {
    ASCENDING,
    DESCENDING,
    NONE;
    public static SortedState fromString(String s) throws MovieExceptions.InvalidSortedStateException {
        switch (s.toLowerCase()) {
            case "ascending":
                return ASCENDING;
            case "descending":
                return DESCENDING;
            case "none":
                return NONE;
            default:
                throw new MovieExceptions.InvalidSortedStateException("Invalid sorted state: " + s);
        }
    }
}