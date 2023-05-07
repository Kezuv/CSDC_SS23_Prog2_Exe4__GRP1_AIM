package at.ac.fhcampuswien.fhmdb.models;

public class User {
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
