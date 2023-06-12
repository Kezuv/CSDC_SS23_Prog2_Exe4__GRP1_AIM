package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class NoneSortState implements SortState {


    /**
     * @param sortingState
     */
    @Override
    public void next(SortingState sortingState) {
        sortingState.setState(new AscendingSortState());
    }

    /**
     * @param allMovies
     */
    @Override
    public void sort(ObservableList<Movie> allMovies) {
        // initial state
    }


    /**
     * @return
     */
    @Override
    public String displayText() {
        return "A-Z";
    }
}