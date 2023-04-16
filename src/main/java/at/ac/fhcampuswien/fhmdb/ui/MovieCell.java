package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    private final Label lengthInMinutes = new Label();
    private final ImageView imgView = new ImageView();
    private final VBox layout = new VBox();
    private final VBox imgBox = new VBox();
    private final HBox content = new HBox();
    private final Region setRight = new Region();

    private final HBox header = new HBox();
    private final VBox details = new VBox();
    private final HBox yearAndLenght = new HBox();

    private final HBox team = new HBox();
    private final VBox directorsWriters = new VBox();


    private String setUpList(List<String> type, String description){

        String list = description+": \n";
        for (int i = 0; i < type.size() ; i ++){
            if (i == type.size()-1 ){
                list = list + "     "+type.get(i);
            } else {
                list = list + "     " + type.get(i) + ", \n";
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


            setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    movie.setExpanded(true);
                    updateItem(movie, false);
                }
                if (event.getButton() == MouseButton.SECONDARY){
                    movie.setExpanded(false);
                    updateItem(movie, false);
                }
            });


            //gernal

            //set Text

            title.setText(movie.getTitle());
            description.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            String genres = "Genres: " + movie.getGenres()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.joining(", "));
            genre.setText(genres);

            //set style
            title.getStyleClass().add("text-yellow");
            title.fontProperty().set(title.getFont().font(20));


            description.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            genre.setStyle("-fx-font-style: italic");
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            //Width of the cell´s
            HBox.setHgrow(setRight, Priority.ALWAYS);

            layout.setPrefWidth(getScene().getWindow().getWidth() * 0.975);
            layout.setMinWidth(getScene().getWindow().getWidth() * 0.975);

            //if !isExpanded (small / not selected)
            if (!movie.isExpanded()){

                //set Text
                releaseYear.setText("(" + movie.getReleaseYear() + ")");
                rating.setText(String.valueOf(movie.getRating()));


                //set style
                rating.getStyleClass().add("text-white");
                rating.setFont(Font.font("System", FontWeight.BOLD, 15));
                releaseYear.getStyleClass().clear();
                releaseYear.getStyleClass().add("text-white");
                releaseYear.setFont(Font.font("System", FontWeight.BOLD,15));

                //set layout
                //Region sets the Rating to the right & updating the HBox-settings

                setRight.maxWidthProperty().bind(header.widthProperty().subtract(title.maxWidthProperty()).subtract(releaseYear.maxWidthProperty()).subtract(rating.maxWidthProperty()));

                header.getChildren().clear();
                header.getChildren().addAll(title, releaseYear, setRight, rating);
                header.setAlignment(Pos.BASELINE_LEFT);
                header.spacingProperty().set(5);



                layout.getChildren().clear();
                layout.getChildren().addAll(header, description, genre);

            }
            // if isExpanded (big / selected)
            else {

                //set Text
                directors.setText(setUpList(movie.getDirectors(), "Directors"));
                writers.setText(setUpList(movie.getWriters(), "Writers"));
                mainCast.setText(setUpList(movie.getMainCast(), "Main Cast"));
                releaseYear.setText("Release Year: " + movie.getReleaseYear());
                lengthInMinutes.setText("Length: " + movie.getLengthInMinutes() + " min");



                //set style
                writers.getStyleClass().add("text-white");
                mainCast.getStyleClass().add("text-white");
                directors.getStyleClass().add("text-white");
                releaseYear.getStyleClass().clear();
                releaseYear.getStyleClass().add("text-white");
                releaseYear.fontProperty().set(releaseYear.getFont().font(12));
                lengthInMinutes.getStyleClass().add("text-white");

                //set layout

                try {
                    imgView.setImage(new Image(MovieAPI.getTrueImgUrl(movie.getImgUrl())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imgBox.setMaxWidth(300);
                imgBox.setAlignment(Pos.CENTER);
                imgView.setFitWidth(imgBox.getMaxWidth());
                imgView.setPreserveRatio(true);
                imgView.setSmooth(true);
                imgView.setCache(true);

                content.getChildren().clear();

                        imgBox.getChildren().clear();
                        imgBox.getChildren().add(imgView);
                content.getChildren().add(imgBox);

                        details.getChildren().clear();
                                header.getChildren().clear();

                                setRight.maxWidthProperty().bind(header.widthProperty().subtract(title.maxWidthProperty()).subtract(rating.maxWidthProperty()));
                                header.getChildren().addAll(title, setRight, rating);
                                description.wrapTextProperty().set(true);
                                description.setMaxWidth(550);

                                yearAndLenght.getChildren().clear();
                                yearAndLenght.getChildren().addAll(releaseYear, lengthInMinutes);
                                yearAndLenght.spacingProperty().set(360);


                        details.getChildren().addAll(header, yearAndLenght, description, genre);


                                team.getChildren().clear();
                                        writers.setPadding(new Insets(20,0,0,0));
                                        directorsWriters.getChildren().clear();
                                        directorsWriters.getChildren().addAll(directors, writers);
                                team.getChildren().addAll(directorsWriters, mainCast);
                                team.spacingProperty().set(50);

                        details.getChildren().add(team);
                        details.spacingProperty().set(20);
                        details.prefWidthProperty().set(getScene().getWidth() - imgBox.getMaxWidth());

                content.getChildren().add(details);
                content.spacingProperty().set(50);
                content.setPadding(new Insets(20,0,20,20));

                layout.getChildren().clear();
                layout.getChildren().add(content);



            }

            //size Layout

            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(Pos.CENTER_LEFT);
            setGraphic(layout);


        }
    }

}

