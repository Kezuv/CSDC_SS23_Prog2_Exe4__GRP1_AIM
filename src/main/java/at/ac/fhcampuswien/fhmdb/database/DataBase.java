package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.Exceptions.DBExceptions;
import at.ac.fhcampuswien.fhmdb.entities.MovieEntity;
import at.ac.fhcampuswien.fhmdb.entities.UserEntity;
import at.ac.fhcampuswien.fhmdb.entities.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.repos.WatchlistRepository;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBase {
    public static final String databaseUrl = "jdbc:h2:./fhmdbDataBase/fhmdb";
    public static final String user = "admin";
    public static final String password = "password";

    private static ConnectionSource connectionSource;
    private Dao<UserEntity, Long> userDao;
    private Dao<MovieEntity, String> movieDao;
    private Dao<WatchlistMovieEntity, Long> watchlistDao;


    private static DataBase instance;

    private DataBase() throws DBExceptions.ConnectionException, DBExceptions.TableCreationException, DBExceptions.DaoInitializationException {
        try {
            createConnectionSource();
            userDao = DaoManager.createDao(connectionSource, UserEntity.class);
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException e) {
            throw new DBExceptions.ConnectionException("Error creating connection source: " + e.getMessage());
        } catch (DBExceptions.TableCreationException tce) {
            throw new DBExceptions.TableCreationException("Error creating tables: " + tce.getMessage());
        } catch (Exception ex) {
            throw new DBExceptions.DaoInitializationException("Error initializing DAOs: " + ex.getMessage());
        }
    }

    public static DataBase getDatabaseUser() throws DBExceptions.ConnectionException, DBExceptions.TableCreationException, DBExceptions.DaoInitializationException {
        if(instance == null){
            instance = new DataBase();
        }
        return instance;
    }



    private static void createTables() throws DBExceptions.TableCreationException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
        } catch (SQLException e) {
            throw new DBExceptions.TableCreationException("Error creating tables: " + e.getMessage());
        }
    }

    private static void createConnectionSource() throws DBExceptions.ConnectionException {
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl, user, password);
        } catch (SQLException e) {
            throw new DBExceptions.ConnectionException("Error creating connection source: " + e.getMessage());
        }
    }

    public Dao<UserEntity, Long> getUserDao() {
        return this.userDao;
    }
    public Dao<MovieEntity, String> getMovieDao() {
        return this.movieDao;
    }

    public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return watchlistDao;
    }
}
