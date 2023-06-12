package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.util.Comparator;

public class Sort {
    // define the sortState interface
    public static interface SortState {
        void sort(ObservableList<Movie> observableMovies);
    }
    public static interface Sortable {
        void setSortState(SortState sortState);
        SortState getSortState();
        Button getSortBtn();
        void sortMovies();

        default void sortBtnClicked(ActionEvent actionEvent) {
            SortState sortState = getSortState();
            Button sortBtn = getSortBtn();

            if (sortState instanceof Sort.NoneSortState || sortState instanceof Sort.DescendingSortState) {
                setSortState(new Sort.AscendingSortState());
                sortBtn.setText("A-Z");
            } else if (sortState instanceof Sort.AscendingSortState) {
                setSortState(new Sort.DescendingSortState());
                sortBtn.setText("Z-A");
            }
            sortMovies();
        }
    }

    // define concrete states
    public static class AscendingSortState implements Sort.SortState {
        @Override
        public void sort(ObservableList<Movie> observableMovies) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
        }
    }

    public static class DescendingSortState implements Sort.SortState {
        @Override
        public void sort(ObservableList<Movie> observableMovies) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }
    }

    public static class NoneSortState implements Sort.SortState {
        @Override
        public void sort(ObservableList<Movie> observableMovies) {
            // Do nothing
        }
    }
}

