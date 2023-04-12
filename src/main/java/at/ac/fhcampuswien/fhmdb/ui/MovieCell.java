package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label description = new Label();
    private final Label genre = new Label();
    private final Label directors = new Label();
    private final Label writers = new Label();
    private final Label mainCast = new Label();
    private final Label rating = new Label();
    private final Label releaseYear = new Label();
    private final ImageView imgView = new ImageView();
    private final VBox layout = new VBox();
    private boolean isExpanded = false;

    private String setUpList(List<String> type, String description){

        String list = description+": ";
        for (int i = 0; i < type.size() ; i ++){
            if (i == type.size()-1 ){
                list = list + type.get(i);
            } else {
                list = list + type.get(i) + ", ";
            }
        }
        return list;
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setGraphic(null);
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            // set Text
            title.setText(movie.getTitle());
            description.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            String genres = movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);
            if (isExpanded){
                directors.setText(setUpList(movie.getDirectors(), "Directors"));
                writers.setText(setUpList(movie.getWriters(), "Writers"));
                mainCast.setText(setUpList(movie.getMainCast(), "Main Cast"));
                rating.setText("Rating: " + movie.getRating());
                releaseYear.setText("Release Year: " + movie.getReleaseYear());
            }

            // color scheme
            title.getStyleClass().add("text-yellow");
            description.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            rating.getStyleClass().add("text-white");
            writers.getStyleClass().add("text-white");
            directors.getStyleClass().add("text-white");
            releaseYear.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));


            // layout
            title.fontProperty().set(title.getFont().font(20));
            description.setMaxWidth(this.getScene().getWidth() - 30);
            description.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            if (isExpanded) {
                try {
                    imgView.setImage(new Image(MovieAPI.getTrueImgUrl(movie.getImgUrl())));
                    imgView.setFitHeight(100);
                    imgView.setPreserveRatio(true);
                    imgView.setSmooth(true);
                    imgView.setCache(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                layout.getChildren().clear();
                layout.getChildren().addAll(imgView, title, description, directors, writers, mainCast, rating, releaseYear, genre);
            } else {
                layout.getChildren().clear();
                layout.getChildren().addAll(title, description, genre);
            }

            setGraphic(layout);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        isExpanded = !isExpanded;
        updateItem(getItem(), false);
    }
}

