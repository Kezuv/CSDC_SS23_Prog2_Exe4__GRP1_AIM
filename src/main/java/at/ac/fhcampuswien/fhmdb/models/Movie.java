package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.entities.MovieEntity;
import at.ac.fhcampuswien.fhmdb.repos.MovieRepository;
import at.ac.fhcampuswien.fhmdb.repos.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.ui.controller.MainViewController;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class Movie {
    private final String id, title, description;
    private final String imgUrl;
    private final List<Genre> genres;
    private final List<String> directors, writers, mainCast;
    private final double rating;
    private final int releaseYear, lengthInMinutes;
    private boolean isExpanded;
    private boolean onWatchList;

    // Package-private constructor
    public Movie(String id, String title, String description, String imgUrl, List<Genre> genres, List<String> directors, List<String> writers, List<String> mainCast, double rating, int releaseYear, int lengthInMinutes) throws IOException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.genres = genres;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.lengthInMinutes = lengthInMinutes;
    }

    public MovieEntity returnAsMovieEntitiy(){
        return new MovieEntity(id, title, description, imgUrl,
                MovieRepository.genresToString(genres),
                MovieRepository.listToString(directors),
                MovieRepository.listToString(writers),
                MovieRepository.listToString(mainCast),
                rating, releaseYear, lengthInMinutes);
    }

    public String getId() {return id;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isOnWatchList() {
        return onWatchList;
    }

    public void upDateOnWatchList() {
        try {
            onWatchList = WatchlistRepository.checkIfMovieIsOnWatchList(MainViewController.getActiveUser(), this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Movie> initializeMovies(MovieFactory factory, String data) throws IOException {
        Gson gson = new Gson();
        Movie[] movies = gson.fromJson(data, Movie[].class);
        for(Movie movie: movies){
            factory.createMovie(movie.id, movie.title, movie.description, movie.imgUrl, movie.genres, movie.directors, movie.writers, movie.mainCast, movie.rating, movie.releaseYear, movie.lengthInMinutes);
        }
        return Arrays.asList(movies);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Movie other)) {
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public static class DefaultMovieFactory implements MovieFactory {
        @Override
        public Movie createMovie(String id, String title, String description, String imgUrl, List<Genre> genres, List<String> directors, List<String> writers, List<String> mainCast, double rating, int releaseYear, int lengthInMinutes) throws IOException {
            try {
                return new Movie(id, title, description, imgUrl, genres, directors, writers, mainCast, rating, releaseYear, lengthInMinutes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public interface MovieFactory {
        Movie createMovie(String id, String title, String description, String imgUrl, List<Genre> genres, List<String> directors, List<String> writers, List<String> mainCast, double rating, int releaseYear, int lengthInMinutes) throws IOException;
    }
}
