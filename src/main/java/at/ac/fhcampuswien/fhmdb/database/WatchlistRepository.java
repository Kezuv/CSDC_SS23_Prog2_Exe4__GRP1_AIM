package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    private Dao<WatchlistMovieEntity, Long> watchlistDao;

    public WatchlistRepository (ConnectionSource connectionSource) throws SQLException {
        this.watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
    }

    public List<MovieEntity> getAllMoviesForUser(UserEntity user) throws SQLException {
        List<MovieEntity> movies = new ArrayList<>();
        QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = watchlistDao.queryBuilder();
        queryBuilder.where().eq("username", user.getUsername());
        List<WatchlistMovieEntity> watchlist = queryBuilder.query();
        for (WatchlistMovieEntity watchlistMovie : watchlist) {
            movies.add(watchlistMovie.getMovie());
        }
        return movies;
    }

    public boolean add(WatchlistMovieEntity watchlistMovie) throws SQLException {
        if (watchlistDao.idExists(watchlistMovie.getId())) {
            return false; // already exists
        }
        watchlistDao.create(watchlistMovie);
        return true;
    }

    public boolean addMovieForUser(UserEntity user, MovieEntity movie) throws SQLException {
        WatchlistMovieEntity watchlistMovie = new WatchlistMovieEntity(user, movie);
        return add(watchlistMovie);
    }

    public boolean delete(WatchlistMovieEntity watchlistMovie) throws SQLException {
        if (!watchlistDao.idExists(watchlistMovie.getId())) {
            return false; // doesn't exist
        }
        watchlistDao.delete(watchlistMovie);
        return true;
    }

    public boolean deleteMovieForUser(UserEntity user, MovieEntity movie) throws SQLException {
        QueryBuilder<WatchlistMovieEntity, Long> queryBuilder = watchlistDao.queryBuilder();
        queryBuilder.where().eq("username", user.getUsername()).and().eq("id", movie.getId());
        List<WatchlistMovieEntity> watchlist = queryBuilder.query();
        if (watchlist.isEmpty()) {
            return false; // not found
        }
        WatchlistMovieEntity watchlistMovie = watchlist.get(0);
        return delete(watchlistMovie);
    }
}

