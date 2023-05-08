package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseUser {
    public static final String databaseUrl = "jdbc:h2:file ./db/usersdb";
    public static final String user = "user";
    public static final String password = "password";

    private static ConnectionSource connectionSource;
    private Dao<UserEntity, String> userDao;

    private static DatabaseUser instance;

    private DatabaseUser(){
        try{
            createConnectionSource();
            userDao = DaoManager.createDao(connectionSource, UserEntity.class);
            createTables();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseUser getDatabaseUser(){
        if(instance == null){
            instance = new DatabaseUser();
        }
        return instance;
    }

    public Dao<UserEntity, String> getUserDao() {
        return this.userDao;
    }

    private static void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, UserEntity.class);
    }
    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(databaseUrl, user, password);
    }
}
