package at.ac.fhcampuswien.fhmdb.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Movies")
public class MovieEntity {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String imgUrl;
    @DatabaseField
    private String genres;
    @DatabaseField
    private String directors;
    @DatabaseField
    private String writers;
    @DatabaseField
    private String mainCast;
    @DatabaseField
    private double rating;
    @DatabaseField
    private int releaseYear;
    @DatabaseField
    private int lengthInMinutes;

    public MovieEntity(){}

    public MovieEntity(String id, String title, String description, String imgUrl, String genres,
                       String directors, String writers, String mainCast, double rating,
                       int releaseYear, int lengthInMinutes){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.genres = genres;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getId(){return id;}

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getGenres() {
        return genres;
    }

    public String getDirectors() {
        return directors;
    }

    public String getWriters() {
        return writers;
    }

    public String getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }
}
