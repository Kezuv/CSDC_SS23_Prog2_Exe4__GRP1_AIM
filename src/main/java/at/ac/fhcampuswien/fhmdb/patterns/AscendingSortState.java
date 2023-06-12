package at.ac.fhcampuswien.fhmdb.patterns;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class AscendingSortState implements SortState {
    /**
     * @param sortingState
     */
    @Override
    public void next(SortingState sortingState) {
        sortingState.setState(new DescendingSortState());
    }

    /**
     * @param allMovies
     */
    @Override
    public void sort(ObservableList<Movie> allMovies) {
        allMovies.sort(Comparator.comparing(Movie::getTitle));
    }


    /**
     * @return
     */
    @Override
    public String displayText() {
        return "A-Z";
    }
    // checks state (if None or Desc) and returns next state

}
