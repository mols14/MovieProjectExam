package sample.gui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.be.Movie;
import sample.gui.model.MovieModel;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements initializable {

    private MovieModel movieModel;
    private ObservableList observableListMovies;

    @Override
    public void initializable(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList = movieModel.getMovies();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    }


    public Button closeButton;

    public void handleSearch(KeyEvent keyEvent) throws IOException {
        if (filterSearch.getText() == null || filterSearch.getText().length() <= 0) {
            tableViewMovies.setItems(movieModel.getMovies());
        } else {
            ObservableList<Movie> foundMovieList = movieModel.filter(movieModel.getMovies(), filterSearch.getText());

            tableViewMovies.setItems(foundMovieList);

        }
    }

    public void handleClose(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
