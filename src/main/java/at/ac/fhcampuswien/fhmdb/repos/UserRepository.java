package at.ac.fhcampuswien.fhmdb.repos;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.DataBase;
import at.ac.fhcampuswien.fhmdb.entities.UserEntity;
import at.ac.fhcampuswien.fhmdb.models.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    public static Dao<UserEntity, Long> userDao;

    static {
        try {
            userDao = DataBase.getDatabaseUser().getUserDao();
        } catch (DatabaseException.ConnectionException | DatabaseException.TableCreationException |
                 DatabaseException.DaoInitializationException e) {
            throw new RuntimeException(e);
        }
    }


    //Register new user in database (Need User Object)
    public static void registerUser(String username, String password) throws DatabaseException.RegisterUserException {
        try {
            userDao.createIfNotExists(new UserEntity(username, password));
        } catch (SQLException e) {
            throw new DatabaseException.RegisterUserException("Failed to register user", e);
        }
    }

    //Converts User object to UserEntity object
    public static UserEntity userToUserEntity (User user){
        return new UserEntity(user.getId(), user.getUsername(), user.getPassword());
    }

    public static User userLogIn(String username, String password) throws DatabaseException.UserLoginException {
        try {
            List<UserEntity> allUsers = userDao.queryForMatching(new UserEntity(username, password));
            if (!allUsers.isEmpty()) {
                UserEntity userEntity = allUsers.get(0);
                return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getId());
            } else {
                throw new DatabaseException.UserLoginException("Username or password is wrong!");
            }
        } catch (SQLException e) {
            throw new DatabaseException.RegisterUserException("User not found: ", e);
        } catch (IndexOutOfBoundsException e) {
            throw new DatabaseException.UserLoginException("User not found: "+ e.getMessage());
        }
    }

    //Included for purposes (Converts UserEntity to User object)
    public static User userEntityToUser (UserEntity user){
        return new User(user.getUsername(), user.getPassword());
    }

    public static boolean isUserExists(String username) {
        try {
            // Query the user table for a user with the given username
            List<UserEntity> users = userDao.queryForEq("username", username);

            // Return true if at least one user is found
            return !users.isEmpty();
        } catch (SQLException e) {
            // Handle the exception or rethrow it
            throw new RuntimeException("Error checking if user exists: " + e.getMessage(), e);
        }
    }
}
