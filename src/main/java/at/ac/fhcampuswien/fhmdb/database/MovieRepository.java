package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    Dao<MovieEntity, String> movieDao;

    public MovieRepository(){
        this.movieDao = DatabaseMovie.getDatabaseMovie().getMovieDao();
    }

    public void addToMovie (Movie movie) throws SQLException {
        movieDao.create(movieToMovie(movie));
    }

    public MovieEntity getMovie (String id) throws SQLException{
        return movieDao.queryForId(id);
    }

    private MovieEntity movieToMovie (Movie movie){
        return new MovieEntity(movie.getId(), movie.getTitle(), movie.getDescription(),
                movie.getImgUrl(), genresToString(movie.getGenres()), listToString(movie.getDirectors()), listToString(movie.getWriters()),
                listToString(movie.getMainCast()), movie.getRating(), movie.getReleaseYear(), movie.getLengthInMinutes());
    }

    private String genresToString (List<Genre> genres){
        StringBuilder genreString = new StringBuilder();
        for (Genre genre : genres) {
            genreString.append(genre.name()).append(",");
        }
        String result = genreString.toString().replaceAll(", $", "");
        return result;
    }

    private String listToString (List<String> list){
        return String.join(",", list);
    }
}
