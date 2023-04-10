package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {

    private final String id, title, description;

    private final String imgUrl;
    private final List<Genre> genres;
    private final List<String> directors, writers, mainCast;
    private final double rating;
    private final int releaseYear, lengthInMinutes;

    public Movie(String id, String title, String description, String imgUrl, List<Genre> genres, List<String> directors, List<String> writers, List<String> mainCast, double rating, int releaseYear, int lengthInMinutes) {
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(String data){

        Gson gson = new Gson();

        Movie[] movies = gson.fromJson(data, Movie[].class);

        List<Movie> moviesList = new ArrayList<>();
        for (Movie movie : movies){
            moviesList.add(movie);
        }

        return moviesList;
    }


    public String getId() {
        return id;
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

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }
}
