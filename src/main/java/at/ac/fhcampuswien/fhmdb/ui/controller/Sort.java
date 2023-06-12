package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;


import java.util.Comparator;

public class Sort {
    // define the sortState interface
    public  interface SortState {
        void sort(ObservableList<Movie> observableMovies);
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

