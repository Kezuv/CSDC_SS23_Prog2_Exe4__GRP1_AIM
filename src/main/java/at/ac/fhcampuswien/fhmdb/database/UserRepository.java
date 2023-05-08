package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class UserRepository {
    Dao<UserEntity, String> userDao;

    public UserRepository(){
        this.userDao = DatabaseUser.getDatabaseUser().getUserDao();
    }

    public void addToUsers(User user) throws SQLException {
        userDao.create(userToUser(user));
    }

    public UserEntity getUserbyUsername(String username) throws SQLException{
        return userDao.queryForId(username);
    }

    private UserEntity userToUser (User user){
        return new UserEntity(user.getUsername(), user.getPassword());
    }
}
