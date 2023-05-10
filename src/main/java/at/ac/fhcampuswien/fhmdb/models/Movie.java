package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieExceptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private final String id, title, description;
    private final String imgUrl;
    private final List<Genre> genres;
    private final List<String> directors, writers, mainCast;
    private final double rating;
    private final int releaseYear, lengthInMinutes;
    private boolean isExpanded;

    public Movie(String id, String title, String description, String imgUrl, List<Genre> genres, List<String> directors, List<String> writers, List<String> mainCast, double rating, int releaseYear, int lengthInMinutes) throws MovieExceptions.MovieNotFoundException, InvalidParameterException, IOException {
        if (id == null || id.isEmpty() || title == null || title.isEmpty() || genres == null || genres.isEmpty() || rating < 0 || rating > 10 || releaseYear < 0 || lengthInMinutes < 0) {
            throw new InvalidParameterException("Invalid parameters when creating a movie.");
        }

        if (imgUrl == null || imgUrl.isEmpty()) {
            throw new MovieExceptions.MovieNotFoundException("Movie image not found.");
        }

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

    public static List<Movie> initializeMovies(String data) throws InvalidParameterException {
        if (data == null || data.isEmpty()) {
            throw new InvalidParameterException("Invalid parameter when initializing movies.");
        }

        Gson gson = new Gson();
        Movie[] movies = gson.fromJson(data, Movie[].class);
        List<Movie> moviesList = new ArrayList<>();
        for (Movie movie : movies) {
            moviesList.add(movie);
        }
        return moviesList;
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
}
