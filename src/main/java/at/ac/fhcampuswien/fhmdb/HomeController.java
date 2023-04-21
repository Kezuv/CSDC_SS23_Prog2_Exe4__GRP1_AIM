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
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    public Label directorsCount = new Label();
    @FXML
    public Label longestTitle = new Label();
    @FXML
    public Label titleCount = new Label();
    @FXML
    public Label mostPopularActor = new Label();
    @FXML
    public JFXListView movieListView;
    @FXML
    public JFXComboBox<String> genreComboBox;
    @FXML
    public JFXComboBox releaseYearComboBox;
    @FXML
    public JFXComboBox<java.io.Serializable> yearRangeComboBox;
    @FXML
    public JFXComboBox<String> ratingComboBox;
    @FXML
    public JFXComboBox<String> ratingRangeComboBox;
    @FXML
    public JFXComboBox countDirectorsMovie;
    @FXML
    public JFXButton sortBtn;
    @FXML
    public HBox content;
    @FXML
    private  HBox directorsHBox = new HBox();
    public List<Movie> allMovies;
    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();
    protected SortedState sortedState;

    public void initializeState() throws IOException {
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
            return cell;
        });

        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("No filter");// add "no filter" to the genre combobox
        for (int i = 0; i < genres.length; i ++) {
            genreComboBox.getItems().addAll(genres[i].name());    // add all genres to the genre combobox
        }

        releaseYearComboBox.setPromptText("Year"); // set the prompt text for the year combobox
        releaseYearComboBox.getItems().add("No filter"); // add "no filter" to the year combobox
        for(int year = LocalDate.now().getYear(); year >= 1950; year--) {
            releaseYearComboBox.getItems().add(String.valueOf(year)); // add each year from 1950 to current year to the year combobox
        }

        yearRangeComboBox.setPromptText("Year"); // set the prompt text for the year combobox
        yearRangeComboBox.getItems().add("No filter"); // add "No filter"

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
        }
        });

        countDirectorsMovie.setPromptText("Directors Movie Count"); // set the prompt text for the year combobox
        countDirectorsMovie.getItems().clear();
        countDirectorsMovie.getItems().add("No filter"); // add "no filter" to the year combobox
        countDirectorsMovie.getItems().addAll(getDirectorsNames(allMovies));
        directorsCount.setText("Total: 0");

        countDirectorsMovie.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.equals("No filter") || !newValue.equals(null)) {
                directorsCount.setText("Total: " + countMoviesFrom(allMovies, newValue.toString()));
            }
        });

        ratingComboBox.setPromptText("Min"); // set the prompt text for the year combobox
        ratingComboBox.getItems().add("No filter"); // add "no filter" to the year combobox
        for(double rating = 10.0; rating >= 0.5; rating -= 0.5) {
            ratingComboBox.getItems().add(String.valueOf(rating)); // add each rating from 10.0 to 0.5 to the rating combobox
        }

        ratingRangeComboBox.setPromptText("Max"); // set the prompt text for the year combobox
        ratingRangeComboBox.getItems().add("No filter"); // add "no filter" to the year combobox
        ratingComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.equals("No filter")) {
                double ratingComboBox = Double.parseDouble(String.valueOf(newValue));
                ratingRangeComboBox.getItems().clear();
                ratingRangeComboBox.getItems().add("No filter");
                for (double rating = 10; rating >= ratingComboBox; rating-= 0.5) {
                    ratingRangeComboBox.getItems().add(String.valueOf(rating));
                }
                ratingRangeComboBox.setDisable(false);
            } else {
                ratingRangeComboBox.getItems().clear();
                ratingRangeComboBox.getItems().add(null); // add a null value for the "No filter" option
            }
        });

        countDirectorsMovie.setPromptText("Directors Movie Count"); // set the prompt text for the year combobox
        countDirectorsMovie.getItems().clear();
        countDirectorsMovie.getItems().add("No filter"); // add "no filter" to the year combobox
        countDirectorsMovie.getItems().addAll(getDirectorsNames(allMovies));

        mostPopularActor.setText("Most Popular Actor: " + getMostPopularActor(allMovies));
        longestTitle.setText("Longest Movie Title: " + getLongestMovieTitleName(allMovies));
        titleCount.setText("Total: " + getLongestMovieTitle(allMovies));

        directorsHBox.setPadding(new Insets(0,0,0,5));
        content.spacingProperty().set(20);
        content.setPadding(new Insets(10));
    }

    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else if (sortedState == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public List<Movie> filterByRating(List<Movie> movies, double minRating, double maxRating) {
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getRating() >= minRating && movie.getRating() < maxRating)
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
                /*.replaceAll() would remove the spaces in the String and
                .trim() before .length() would remove the spaces before and after the String*/
                .mapToInt(movie -> movie.getTitle().trim().length())
                .max()
                .orElse(0);//<- If nothing is there
    }

    public String getLongestMovieTitleName(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .map(String::trim)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
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

    public static List<String> getDirectorsNames(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getDirectors().stream())
                .distinct() // <- removes duplicates
                .sorted() // <- sort
                .collect(Collectors.toList());
    }

    public void searchBtnClicked(ActionEvent actionEvent) throws IOException {
        String searchQuery =  searchField.getText().trim().toLowerCase();
        if (searchQuery != null && !searchQuery.equals("")) {
            MovieAPI.addParam(SearchParameter.CUSTOMSEARCH, searchQuery);
        }

        String genre = genreComboBox.getSelectionModel().getSelectedItem();
        if (genre != null && !genre.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.GENRE, genre);
        }

        String minRating = ratingComboBox.getSelectionModel().getSelectedItem();
        if (minRating != null && !minRating.equals("No filter")) {
            MovieAPI.addParam(SearchParameter.RATING, minRating);
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

        String maxRatingStr = ratingRangeComboBox.getSelectionModel().getSelectedItem() != null ? ratingRangeComboBox.getSelectionModel().getSelectedItem().toString() : "No filter";
        if (maxRatingStr != null && !maxRatingStr.equals("No filter")){
            allMovies = filterByRating(allMovies, Double.parseDouble(minRating), Double.parseDouble(maxRatingStr));
        }

        directorsCount.setText("");
        countDirectorsMovie.setValue("");
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;

        mostPopularActor.setText("Most Popular Actor: " + getMostPopularActor(allMovies));
        longestTitle.setText("Longest Movie Title: " + getLongestMovieTitleName(allMovies));
        titleCount.setText("Total: " + getLongestMovieTitle(allMovies));

        countDirectorsMovie.setPromptText("Directors Movie Count"); // set the prompt text for the year combobox
        countDirectorsMovie.getItems().clear();
        countDirectorsMovie.getItems().add("No filter"); // add "no filter" to the year combobox
        countDirectorsMovie.getItems().addAll(getDirectorsNames(allMovies));
        directorsCount.setText("Total: ");

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