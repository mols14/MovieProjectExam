package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.be.Category;
import sample.be.Movie;
import sample.gui.model.CategoryModel;
import sample.gui.model.MovieModel;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewMovieEditController {
    public TextField txtFieldMovieTitleEdit;
    public Button newMovieCancelEdit;
    public Button newMovieDoneEdit;
    public TextField txtFieldNewMoviePersonalRatingEdit;
    public DatePicker datePickerEdit;
    public TextField txtCategoryId;
    public TextField txtURLEdit;
    public TextField txtMovieId;
    MovieModel movieModel;
    CategoryModel categorymodel;
    public DatePicker datePickeredit;


    public NewMovieEditController() throws IOException {
        movieModel = new MovieModel();
        categorymodel = new CategoryModel();
    }

    public void handleNewMovieCancelEdit(ActionEvent actionEvent) throws IOException {
        Parent newMovieWindow = FXMLLoader.load(getClass().getResource("/sample/gui/view/MainWindow.fxml")); // Path til fxml filen der tilhøre scenen som skal vises ved tryk på knap
        Scene newSongScene = new Scene(newMovieWindow); // Ny scene som skal vises oprettes
        Stage mainWindowStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainWindowStage.setScene(newSongScene); // Sætter den nye scene
        mainWindowStage.show(); // Viser den nye scene
    }

    public void setMovie(Movie movie){
        txtFieldMovieTitleEdit.setText(movie.getTitle());
        txtFieldNewMoviePersonalRatingEdit.setText(Double.toString(movie.getRating()));
        txtMovieId.setText(Integer.toString(movie.getId()));
        txtURLEdit.setText(movie.getUrl());
    }

    public void handleNewMovieDoneEdit(ActionEvent actionEvent) {
        LocalDate localDate = datePickerEdit.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        try {
            Movie movieToEdit = new Movie(Integer.parseInt(txtMovieId.getText()), txtFieldMovieTitleEdit.getText(), Double.parseDouble(txtFieldNewMoviePersonalRatingEdit.getText()), txtURLEdit.getText(), date);
            movieModel.updateMovie(movieToEdit);
            handleNewMovieCancelEdit(actionEvent);
        }
        catch (Exception exception)
        {
            error("Please fill out all fields");
            exception.printStackTrace();
        }
    }

    public void chooseUrlEdit(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser(); //Opretter ny fileChooser objekt
        fileChooser.setInitialDirectory(new File("Movies/")); //sætter pathen til mappen med sange
        fileChooser.setTitle("Select movie");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "mpeg4")); //filtrerer således filechooeren kun kan tage mp4 og mpeg4 filer
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            txtURLEdit.setText(selectedFile.getName());
        }
    }

    public void error(String text)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.YES);
        alert.showAndWait();
    }
}
