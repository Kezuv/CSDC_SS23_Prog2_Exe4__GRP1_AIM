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


    //Add new user in database (Need User Object)
    public static void addToUsers(User user) throws SQLException {
        userDao.createIfNotExists(userToUserEntity(user));
    }

    //Asks if user exists (Needs a String and returns a UserEntity object -> Getter is there)
    /*public static User getUserbyUsername(String username) throws SQLException{
        UserEntity getUser = userDao.queryForId(username);
        return new User(getUser.getUsername(), getUser.getPassword());
    }
    */
    //Converts User object to UserEntity object
    private static UserEntity userToUserEntity (User user){
        return new UserEntity(user.getUsername(), user.getPassword());
    }

    public static User userLogIn(User userToLogIn) throws SQLException {
            List<UserEntity> allUsers = userDao.queryForMatching(new UserEntity(userToLogIn.getUsername(), userToLogIn.getPassword()));
            return new User(allUsers.get(0).getUsername(), allUsers.get(0).getPassword(), allUsers.get(0).getId());
    }

    //Included for purposes (Converts UserEntity to User object)
    public static User userEntityToUser (UserEntity user){
        return new User(user.getUsername(), user.getPassword());
    }
}
