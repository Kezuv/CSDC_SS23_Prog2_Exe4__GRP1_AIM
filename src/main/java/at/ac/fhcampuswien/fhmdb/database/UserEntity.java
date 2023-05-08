package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Users")
public class UserEntity {
    @DatabaseField(id = true)
    private String username;
    @DatabaseField
    private String password;

    public UserEntity(){};
    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername(){return username;}
    public String getPassword() {
        return password;
    }
}
