package at.ac.fhcampuswien.fhmdb.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Users")
public class UserEntity {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String username;
    @DatabaseField(canBeNull = false)
    private String password;

    public UserEntity(){};
    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserEntity(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername(){return username;}
    public String getPassword() {
        return password;
    }
}
