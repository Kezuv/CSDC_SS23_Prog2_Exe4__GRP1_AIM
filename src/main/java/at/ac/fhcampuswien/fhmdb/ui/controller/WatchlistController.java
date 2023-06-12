package at.ac.fhcampuswien.fhmdb.ui.controller;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.repos.WatchlistRepository;
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
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WatchlistController implements Initializable {
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
    public JFXComboBox countDirectorsMovie;
    @FXML
    public JFXButton sortBtn;
    @FXML
    public HBox content;
    @FXML
    private  HBox directorsHBox = new HBox();
    @FXML
    public Label watchlisterror;
    public List<Movie> watchListMovies;
    protected ObservableList<Movie> observableWatchList = FXCollections.observableArrayList();
    protected Sort.SortState sortState;

    public void setSortState(Sort.SortState sortState) {
        this.sortState = sortState;
    }

    public void initializeState() throws IOException {
        try {
            watchListMovies = WatchlistRepository.getAllMoviesForUser(MainViewController.getActiveUser());
        } catch (SQLException e) {
            watchlisterror.getStyleClass().add("text-red");
            watchlisterror.setText(e.getMessage());
            throw new RuntimeException(e);
        } catch (DatabaseException.UserOperationException gmrue){
            watchlisterror.getStyleClass().add("text-red");
            watchlisterror.setText(gmrue.getMessage());
        }
        observableWatchList.clear();
        observableWatchList.addAll(watchListMovies); // add all movies to the observable list
        for (Movie m : watchListMovies){
            m.upDateOnWatchList();
        }
    }

    public void initializeLayout() {
        movieListView.setItems(observableWatchList);   // set the items of the listview to the observable list

        movieListView.setCellFactory(movieListView -> {
            MovieCell cell = new MovieCell(); // apply custom cells to the listview
            return cell;
        });
        setSortState(new Sort.NoneSortState());

        countDirectorsMovie.setPromptText("Directors Movie Count"); // set the prompt text for the year combobox
        countDirectorsMovie.getItems().clear();
        countDirectorsMovie.getItems().add("No filter"); // add "no filter" to the year combobox
        countDirectorsMovie.getItems().addAll(getDirectorsNames(watchListMovies));

        mostPopularActor.setText("Most Popular Actor: " + getMostPopularActor(watchListMovies));
        longestTitle.setText("Longest Movie Title: " + getLongestMovieTitleName(watchListMovies));
        titleCount.setText("Total: " + getLongestMovieTitle(watchListMovies));

        directorsHBox.setPadding(new Insets(0,0,0,5));
        content.spacingProperty().set(20);
        content.setPadding(new Insets(10));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeLayout();
    }

    public void sortMovies() {
        sortState.sort(observableWatchList);
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
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