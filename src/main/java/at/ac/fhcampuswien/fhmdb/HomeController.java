package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.api.SearchParameter;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    public JFXListView movieListView;
    @FXML
    public JFXComboBox genreComboBox;
    @FXML
    public JFXComboBox releaseYearComboBox;
    @FXML
    public JFXComboBox yearRangeComboBox;
    @FXML
    public JFXComboBox ratingComboBox;
    @FXML
    public JFXButton sortBtn;
    public List<Movie> allMovies;
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    protected SortedState sortedState;

    public void initializeState() throws IOException {
        System.out.println(MovieAPI.getCustomURL());
        allMovies = Movie.initializeMovies(MovieAPI.getApiRequest());
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        Genre[] genres = Genre.values(); // get all genres
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list

        movieListView.setCellFactory(movieListView -> {
            MovieCell cell = new MovieCell(); // apply custom cells to the listview
            cell.setOnMouseClicked(event -> {
                cell.startEdit();
            });
            return cell;
        });

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("No filter");// add "no filter" to the genre combobox
        for (int i = 0; i < genres.length; i ++) {
            genreComboBox.getItems().addAll(genres[i].name());    // add all genres to the genre combobox
        }

        releaseYearComboBox.setPromptText("Filter by Release Year"); // set the prompt text for the year combobox
        releaseYearComboBox.getItems().add("No filter"); // add "no filter" to the year combobox
        for(int year = LocalDate.now().getYear(); year >= 1950; year--) {
            releaseYearComboBox.getItems().add(String.valueOf(year)); // add each year from 1950 to current year to the year combobox
        }

        yearRangeComboBox.setPromptText("Filter movies in range"); // set the prompt text for the year combobox
        yearRangeComboBox.getItems().add("No filter"); // add "No filter"
        yearRangeComboBox.setDisable(true); // disable the range combobox initially

        releaseYearComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
        if (!newValue.equals("No filter")) {
            int releaseYear = Integer.parseInt(String.valueOf(newValue));
            yearRangeComboBox.getItems().clear();
            yearRangeComboBox.getItems().add("No filter");
            for (int i = LocalDate.now().getYear(); i >= releaseYear; i--) {
                yearRangeComboBox.getItems().add(i);
            }
            yearRangeComboBox.setDisable(false);
        } else {
            yearRangeComboBox.getItems().clear();
            yearRangeComboBox.getItems().add("No filter"); // add a null value for the "No filter" option
            yearRangeComboBox.setDisable(true);
        }
        });

        ratingComboBox.setPromptText("Filter by Rating"); // set the prompt text for the year combobox
        ratingComboBox.getItems().add("No filter"); // add "no filter" to the year combobox
        for(double rating = 10.0; rating >= 0.5; rating -= 0.5) {
            ratingComboBox.getItems().add(String.valueOf(rating)); // add each rating from 10.0 to 0.5 to the rating combobox
        }
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else if (sortedState == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public List<Movie> filterByQuery(List<Movie> movies, String query){
        if(query == null || query.isEmpty()) return movies;
        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                    movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }

    public List<Movie> filterByGenre(List<Movie> movies, Genre genre){
        if(genre == null) return movies;
        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }

    public List<Movie> filterByReleaseYear(List<Movie> movies, int releaseYear) {
        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getReleaseYear() == releaseYear)
                .toList();
    }

    public List<Movie> filterByRating(List<Movie> movies, double minRating) {
        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getRating() >= minRating && movie.getRating() < minRating +1)
                .toList();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> {
                    int releaseYear = movie.getReleaseYear();
                    return releaseYear >= startYear && releaseYear <= endYear;
                })
                .toList();
    }







    public void applyAllFilters(String searchQuery, Object genre, Object releaseYear, Object rating, Object endReleaseYear) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }
        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        if (endReleaseYear != null && !endReleaseYear.equals("No filter")) {
            filteredMovies = getMoviesBetweenYears(filteredMovies,
                    Integer.parseInt((String) releaseYear), Integer.parseInt(endReleaseYear.toString()));
        } else if (releaseYear != null && !releaseYear.toString().equals("No filter")) {
            filteredMovies = filterByReleaseYear(filteredMovies, Integer.parseInt(releaseYear.toString()));
        }

        if (rating != null && !rating.toString().equals("No filter")) {
            filteredMovies = filterByRating(filteredMovies, Double.parseDouble(rating.toString()));
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) throws IOException {
        String searchQuery =  searchField.getText().trim().toLowerCase();
        if (searchQuery != null && !searchQuery.equals("")) {
            MovieAPI.addParam(SearchParameter.CUSTOMSEARCH, searchQuery);
        }

        String genre = (String) genreComboBox.getSelectionModel().getSelectedItem();
        if (genre != null && !genre.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.GENRE, genre);
        }

        String releaseYear = (String) releaseYearComboBox.getSelectionModel().getSelectedItem();
        String endReleaseYearStr =  yearRangeComboBox.getSelectionModel().getSelectedItem() != null ? yearRangeComboBox.getSelectionModel().getSelectedItem().toString() : "no filter";
        Integer endReleaseYear = !endReleaseYearStr.equals("no filter") ? Integer.parseInt(endReleaseYearStr) : null;

        if (endReleaseYear != null) {
            for (int i = Integer.parseInt(releaseYear); i ==endReleaseYear; ++i) {
                MovieAPI.addParam(SearchParameter.YEAR, String.valueOf(i));
            }
        } else if (releaseYear != null && !releaseYear.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.YEAR, releaseYear);
        }

        String rating = (String) ratingComboBox.getSelectionModel().getSelectedItem();
        if (rating != null && !rating.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.RATING, rating);
        }

        allMovies = Movie.initializeMovies(MovieAPI.getApiRequest());
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
        applyAllFilters(searchQuery, genre, releaseYear, rating, endReleaseYear);
        if (sortedState != SortedState.NONE) {
            sortMovies();
        }
    }




    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeLayout();
    }
}