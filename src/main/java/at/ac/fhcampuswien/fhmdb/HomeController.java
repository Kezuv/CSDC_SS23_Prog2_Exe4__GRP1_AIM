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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn, directorsBtn;
    @FXML
    public TextField searchField, directorsField;

    @FXML
    public Label directorsCount = new Label();
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
            yearRangeComboBox.getItems().add(null); // add a null value for the "No filter" option
            yearRangeComboBox.setDisable(true);
        }
        });

        ratingComboBox.setPromptText("Filter by Rating"); // set the prompt text for the year combobox
        ratingComboBox.getItems().add("No filter"); // add "no filter" to the year combobox
        for(double rating = 10.0; rating >= 0.5; rating -= 0.5) {
            ratingComboBox.getItems().add(String.valueOf(rating)); // add each rating from 10.0 to 0.5 to the rating combobox
        }

        directorsField.setPromptText("movies from Director");
        directorsBtn.setText("Count");
        directorsCount.setText("Total: ");
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


    public void countDirectorsMovies(){
        long count = countMoviesFrom(allMovies, directorsField.getText());
        directorsCount.setText("Total: " + count);
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

    public String getMostPopularActor(List<Movie> movies){
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");//<- If nothing is there
    }


    public int getLongestMovieTitle(List<Movie> movies){
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().trim().length())//<- .trim() before .length() would remove the spaces too
                .max()
                .orElse(0);//<- If nothing is there
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

    public static long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
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

        String rating = (String) ratingComboBox.getSelectionModel().getSelectedItem();
        if (rating != null && !rating.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.RATING, rating);
        }

        String releaseYear = (String) releaseYearComboBox.getSelectionModel().getSelectedItem();
        String endReleaseYearStr =  yearRangeComboBox.getSelectionModel().getSelectedItem() != null ? yearRangeComboBox.getSelectionModel().getSelectedItem().toString() : "No filter";
        Integer endReleaseYear = !endReleaseYearStr.equals("No filter") ? Integer.parseInt(endReleaseYearStr) : null;

        if (endReleaseYear != null){
            allMovies = Movie.initializeMovies(MovieAPI.getApiRequest());
            allMovies = getMoviesBetweenYears(allMovies, Integer.parseInt(releaseYear), endReleaseYear);

        }
        else if (releaseYear != null && !releaseYear.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.YEAR, releaseYear);
            allMovies = Movie.initializeMovies(MovieAPI.getApiRequest());
        } else {
            allMovies = Movie.initializeMovies(MovieAPI.getApiRequest());
        }

        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;

       // applyAllFilters(searchQuery, genre, releaseYear, rating, endReleaseYear);
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