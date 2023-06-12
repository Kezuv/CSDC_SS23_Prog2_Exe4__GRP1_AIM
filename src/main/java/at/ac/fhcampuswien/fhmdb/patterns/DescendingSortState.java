package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class DescendingSortState implements SortState {


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
        allMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
    }

    /**
     * @return
     */
    @Override
    public String displayText() {
        return "Z-A";
    }
}
