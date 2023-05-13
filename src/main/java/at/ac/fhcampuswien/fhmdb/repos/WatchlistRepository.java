package at.ac.fhcampuswien.fhmdb.repos;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.DataBase;
import at.ac.fhcampuswien.fhmdb.entities.MovieEntity;
import at.ac.fhcampuswien.fhmdb.entities.UserEntity;
import at.ac.fhcampuswien.fhmdb.entities.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    private static Dao<WatchlistMovieEntity, Long> watchlistDao;

    static {
        try {
            watchlistDao = DataBase.getDatabaseUser().getWatchlistDao();
        } catch (DatabaseException.ConnectionException | DatabaseException.TableCreationException |
                 DatabaseException.DaoInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    //Returns all movies in the watchlist for a user
    public static List<Movie> getAllMoviesForUser(User user) throws DatabaseException.GetAllMoviesException, SQLException {
    try {


        List<Movie> movies = new ArrayList<>();

        QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = watchlistDao.queryBuilder();
        queryBuilder.where().eq("USER", user.getId());

        List<WatchlistMovieEntity> watchlist = queryBuilder.query();

        for (WatchlistMovieEntity watchlistMovie : watchlist) {
            movies.add(MovieRepository.movieEntityToMovie(watchlistMovie.getMovie()));
        }
        return movies;
    } catch (SQLException e){
        throw new DatabaseException.GetMoviesForUserException("Failed to retrieve watchlist movies for user: " + user.getUsername(), e);

    } catch (DatabaseException.GetMovieException gme){

    }
    return null;
    }

    public static void addMovieToWatchList(User activeUser, Movie movieToAdd) throws DatabaseException.AddMovieToWatchlistException {
        try {
            watchlistDao.createIfNotExists(new WatchlistMovieEntity(UserRepository.userToUserEntity(activeUser),
                    MovieRepository.movieToMovieEntity(movieToAdd)));
        } catch (SQLException e) {
            throw new DatabaseException.AddMovieToWatchlistException("Failed to add movie to watchlist for user: " + activeUser.getUsername(), e);
        }
    }





    //Removes a movie from the watchlist for a user
    public static boolean removeMovieFromWatchlist(User user, Movie movie) throws DatabaseException.RemoveMovieFromWatchlistException {
        try {
            QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = watchlistDao.queryBuilder();
            queryBuilder.where().eq("USER", user.getId()).and().eq("MOVIEID", movie.getId());
            List<WatchlistMovieEntity> watchlist = queryBuilder.query();

            if (watchlist.isEmpty()) {
                return false; // not found
            }

            WatchlistMovieEntity watchlistMovie = watchlist.get(0);
            watchlistDao.delete(watchlistMovie);

            return true;
        } catch (SQLException e) {
            throw new DatabaseException.RemoveMovieFromWatchlistException("Failed to remove movie from watchlist for user: " + user.getUsername(), e);
        }
    }

    public static boolean checkIfMovieIsOnWatchList(User user, Movie movie) throws SQLException {
        QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = watchlistDao.queryBuilder();
        queryBuilder.where().eq("USER", user.getId()).and().eq("MOVIEID", movie.getId());
        List<WatchlistMovieEntity> watchlist = queryBuilder.query();
        if (watchlist.isEmpty()) {
            return false; // not found
        }
        return true;
    }
}

