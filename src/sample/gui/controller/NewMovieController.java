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
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewMovieController {

    public TextField txtURL;
    public DatePicker datePicker;
    private MovieModel movieModel;
    public TextField txtCategoryId;
    public Button newMovieDone;
    public TextField txtFieldNewMoviePersonalRating;
    public TextField txtFieldMovieTitle;
    private CategoryModel categorymodel;

    public NewMovieController() throws IOException {
        movieModel = new MovieModel();
        categorymodel = new CategoryModel();
    }

    public void handleNewMovieCancel(ActionEvent actionEvent) throws IOException {
        Parent newMovieWindow = FXMLLoader.load(getClass().getResource("/sample/gui/view/MainWindow.fxml")); // Path til fxml filen der tilhøre scenen som skal vises ved tryk på knap
        Scene newSongScene = new Scene(newMovieWindow); // Ny scene som skal vises oprettes
        Stage mainWindowStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainWindowStage.setScene(newSongScene); // Sætter den nye scene
        mainWindowStage.show(); // Viser den nye scene
    }

    public void setCategory(Category selectedCategory) {
        txtCategoryId.setText(Integer.toString(selectedCategory.getCategoryId()));
    }

    public void handleNewMovieDone(ActionEvent actionEvent) throws SQLException, IOException {
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        try {
            Movie createdMovie = movieModel.createMovie(txtFieldMovieTitle.getText(), Double.parseDouble(txtFieldNewMoviePersonalRating.getText()), txtURL.getText(), date);
            categorymodel.addMovieToCategory(Integer.parseInt(txtCategoryId.getText()), createdMovie.getId());
            handleNewMovieCancel(actionEvent);
        }
        catch (Exception exception)
        {
            error("Please fill out all fields");
            exception.printStackTrace();
        }
    }

    public void chooseUrl(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser(); //Opretter ny fileChooser objekt
        fileChooser.setInitialDirectory(new File("Movies/")); //sætter pathen til mappen med sange
        fileChooser.setTitle("Select movie");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "mpeg4")); //filtrerer således filechooeren kun kan tage mp4 og mpeg4 filer
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            txtURL.setText(selectedFile.getName());
        }
    }

    private void error(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.YES);
        alert.showAndWait();
    }
}
