package at.ac.fhcampuswien.fhmdb.database;

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

    private DataBase(){
        try{
            createConnectionSource();
            userDao = DaoManager.createDao(connectionSource, UserEntity.class);
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
           // watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static DataBase getDatabaseUser(){
        if(instance == null){
            instance = new DataBase();
        }
        return instance;
    }



    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
      //  TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }

    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(databaseUrl, user, password);
    }

    public Dao<UserEntity, Long> getUserDao() {
        return this.userDao;
    }
    public Dao<MovieEntity, String> getMovieDao() {
        return this.movieDao;
    }

    /*public Dao<WatchlistMovieEntity, Long> getWatchlistDao() {
        return watchlistDao;
    }*/
}
