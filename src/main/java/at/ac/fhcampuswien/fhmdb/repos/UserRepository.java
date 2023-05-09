package at.ac.fhcampuswien.fhmdb.repos;

import at.ac.fhcampuswien.fhmdb.database.DataBase;
import at.ac.fhcampuswien.fhmdb.entities.UserEntity;
import at.ac.fhcampuswien.fhmdb.models.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    public static Dao<UserEntity, Long> userDao;

    static {
        userDao = DataBase.getDatabaseUser().getUserDao();
    }


    //Register new user in database (Need User Object)
    public static void registerUser(String username, String password) throws SQLException {
        userDao.createIfNotExists(new UserEntity(username,password));
    }

    //Converts User object to UserEntity object
    private static UserEntity userToUserEntity (User user){
        return new UserEntity(user.getUsername(), user.getPassword());
    }

    public static User userLogIn(String username, String password) throws SQLException {
            List<UserEntity> allUsers = userDao.queryForMatching(new UserEntity(username, password));
            // TODO what happend when two users have the same username & password?
            return new User(allUsers.get(0).getUsername(), allUsers.get(0).getPassword(), allUsers.get(0).getId());
    }

    //Included for purposes (Converts UserEntity to User object)
    public static User userEntityToUser (UserEntity user){
        return new User(user.getUsername(), user.getPassword());
    }
}
