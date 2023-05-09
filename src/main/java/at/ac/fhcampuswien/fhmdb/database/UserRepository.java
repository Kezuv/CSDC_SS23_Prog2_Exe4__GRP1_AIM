package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class UserRepository {
    public static Dao<UserEntity, String> userDao;

    static {
        userDao = DatabaseUser.getDatabaseUser().getUserDao();
    }

    //Add new user in database (Need User Object)
    public static void addToUsers(User user) throws SQLException {
        userDao.create(userToUserEntity(user));
    }

    //Asks if user exists (Needs a String and returns a UserEntity object -> Getter is there)
    public static User getUserbyUsername(String username) throws SQLException{
        UserEntity getUser = userDao.queryForId(username);
        return new User(getUser.getUsername(), getUser.getPassword());
    }

    //Converts User object to UserEntity object
    private static UserEntity userToUserEntity (User user){
        return new UserEntity(user.getUsername(), user.getPassword());
    }
    //Included for purposes (Converts UserEntity to User object)
    public static User userEntityToUser (UserEntity user){
        return new User(user.getUsername(), user.getPassword());
    }
}
