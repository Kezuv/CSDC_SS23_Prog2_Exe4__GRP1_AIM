package at.ac.fhcampuswien.fhmdb.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    long id;
    @DatabaseField(foreign = true, columnName = "User", canBeNull = false, foreignAutoRefresh = true)
    private UserEntity user;
    @DatabaseField(foreign = true, columnName = "MovieId", canBeNull = false, foreignAutoRefresh = true)
    private MovieEntity movie;

    public WatchlistMovieEntity(){};

    public WatchlistMovieEntity(UserEntity user, MovieEntity movie) {
        this.user = user;
        this.movie = movie;
    }

    public long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
