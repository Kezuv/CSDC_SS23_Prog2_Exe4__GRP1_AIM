package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseMovie {
    public static final String databaseUrl = "jdbc:h2:file ./db/moviedb";
    public static final String user = "user";
    public static final String password = "password";

    private static ConnectionSource connectionSource;
    private Dao<MovieEntity, String> movieDao;

    private static DatabaseMovie instance;

    private DatabaseMovie(){
        try{
            createConnectionSource();
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseMovie getDatabaseMovie(){
        if(instance == null){
            instance = new DatabaseMovie();
        }
        return instance;
    }

    public Dao<MovieEntity, String> getMovieDao() {
        return this.movieDao;
    }

    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
    }
    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(databaseUrl, user, password);
    }
}
