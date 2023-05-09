package at.ac.fhcampuswien.fhmdb.repos;

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

    static{
        movieDao = DataBase.getDatabaseUser().getMovieDao();
    }

    //Add a single movie to database
    public void addToMovie (Movie movie) throws SQLException {
        movieDao.create(movieToMovieEntity(movie));
    }

    //Add all movies to database
    public static void addMovies(List<Movie> movies) throws SQLException {
        for (Movie movie : movies) {
            movieDao.createIfNotExists(movieToMovieEntity(movie));
        }
    }

    //Gets MovieEntity with id (FHMD) for database check etc...
    public MovieEntity getMovieEntity (String id) throws SQLException{
        return movieDao.queryForId(id);
    }

    //Get movie object with id (FHMD) for usages? maybe?
    public Movie getMovie (String id) throws SQLException, IOException {
        return movieEntityToMovie(movieDao.queryForId(id));
    }

    //In-class function to get all MovieEntity´s from the database
    private List<MovieEntity> getAllMovieEntities() throws SQLException {
        return movieDao.queryForAll();
    }

    //Get all movies from database as a List<Movie>
    public List<Movie> getAllMovies() throws SQLException, IOException {
        List<MovieEntity> movieEntities = getAllMovieEntities();
        List<Movie> movies = new ArrayList<>();

        for (MovieEntity entity : movieEntities) {
            Movie movie = movieEntityToMovie(entity);
            movies.add(movie);
        }
        return movies;
    }

    //Converts Movie object to MovieEntity
    private static MovieEntity movieToMovieEntity(Movie movie){
        return new MovieEntity(movie.getId(), movie.getTitle(), movie.getDescription(),
                movie.getImgUrl(), genresToString(movie.getGenres()), listToString(movie.getDirectors()), listToString(movie.getWriters()),
                listToString(movie.getMainCast()), movie.getRating(), movie.getReleaseYear(), movie.getLengthInMinutes());
    }

    //Converts MovieEntity to movie object -> For watchlist usage maybe?
    public Movie movieEntityToMovie (MovieEntity movie) throws IOException {
        return new Movie(movie.getId(), movie.getTitle(), movie.getDescription(),
                movie.getImgUrl(), stringToGenres(movie.getGenres()), stringToList(movie.getDirectors()), stringToList(movie.getWriters()),
                stringToList(movie.getMainCast()), movie.getRating(), movie.getReleaseYear(), movie.getLengthInMinutes());
    }

    //In-class converter from List<Genre> to String
    private static String genresToString(List<Genre> genres){
        StringBuilder genreString = new StringBuilder();
        for (Genre genre : genres) {
            genreString.append(genre.name()).append(",");
        }
        String result = genreString.toString().replaceAll(", $", "");
        return result;
    }

    //In-class converter from String to List<Genre>
    private List<Genre> stringToGenres(String genreString) {
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
    private List<String> stringToList (String str){
        return Arrays.asList(str.split(","));
    }

}
