package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public  interface SortState {
    void next(SortingState sortingState);
    void sort(ObservableList<Movie> allMovies);
    String displayText();
}
