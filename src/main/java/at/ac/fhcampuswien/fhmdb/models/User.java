package at.ac.fhcampuswien.fhmdb.models;

public class User {
    private String username;
    private String password;
    private long id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, long id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }
}
