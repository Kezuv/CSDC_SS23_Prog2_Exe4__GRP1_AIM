package at.ac.fhcampuswien.fhmdb.repos;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.DataBase;
import at.ac.fhcampuswien.fhmdb.entities.MovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieRepository {
    public static Dao<MovieEntity, String> movieDao;

    static {
        try {
            movieDao = DataBase.getDatabaseUser().getMovieDao();
        } catch (Exception e) {
            DatabaseException.handleRepositoryException(e);
        }
    }


    //Add all movies to database
    public static void addMovies(List<Movie> movies) {
        try {
            for (Movie movie : movies) {
                movieDao.createIfNotExists(movieToMovieEntity(movie));
            }
        } catch (SQLException e) {
            throw new DatabaseException.AddMovieException(e.getMessage());
        }
    }


    //In-class function to get all MovieEntity´s from the database
    private static List<MovieEntity> getAllMovieEntities() {
        try {
            return movieDao.queryForAll();
        } catch (SQLException e) {
            throw new DatabaseException.GetAllMoviesException(e.getMessage());
        }
    }

    //Converts Movie object to MovieEntity
    public static MovieEntity movieToMovieEntity(Movie movie){
        return new MovieEntity(movie.getId(), movie.getTitle(), movie.getDescription(),
                movie.getImgUrl(), genresToString(movie.getGenres()), listToString(movie.getDirectors()), listToString(movie.getWriters()),
                listToString(movie.getMainCast()), movie.getRating(), movie.getReleaseYear(), movie.getLengthInMinutes());
    }

    //Converts MovieEntity to movie object -> For watchlist usage maybe?
    public static Movie movieEntityToMovie(MovieEntity movie) {
        try {
            return new Movie(movie.getId(), movie.getTitle(), movie.getDescription(),
                    movie.getImgUrl(), stringToGenres(movie.getGenres()), stringToList(movie.getDirectors()), stringToList(movie.getWriters()),
                    stringToList(movie.getMainCast()), movie.getRating(), movie.getReleaseYear(), movie.getLengthInMinutes());
        } catch (IOException e) {
            throw new DatabaseException.GetMovieException(e.getMessage());
        }
    }


    //In-class converter from List<Genre> to String
    public static String genresToString(List<Genre> genres) {

            StringBuilder genreString = new StringBuilder();
            for (Genre genre : genres) {
                genreString.append(genre.name()).append(",");
            }
            String result = genreString.toString().replaceAll(", $", "");
            return result;

    }


    //In-class converter from String to List<Genre>
    private static List<Genre> stringToGenres(String genreString) {
        List<Genre> genres = new ArrayList<>();
        String[] genreNames = genreString.split(",");
        for (String genreName : genreNames) {
            Genre genre = Genre.valueOf(genreName.trim());
            genres.add(genre);
        }
        return genres;
    }

    //In-class converter from List<String> to String
    private static String listToString(List<String> list){
        return String.join(",", list);
    }

    //In-class converter from String to List<String>
    private static List<String> stringToList(String str){
        return Arrays.asList(str.split(","));
    }

}
