package sample.gui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.be.Movie;
import sample.gui.model.MovieModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements MainWindowControllerInterface {

    public TableView<Movie> tableViewMovies;
    public TableColumn<String, Movie> movieTitleCol;
    public TextField filter;
    private MovieModel movieModel;
    private ObservableList observableListMovies;
    public Button closeButton;

    public MainWindowController() throws IOException {
        tableViewMovies = new TableView<>();
        movieModel = new MovieModel();
    }

    
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
             observableListMovies = movieModel.getMovies();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    }




    public void handleClose(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void clickNewCategory(javafx.event.ActionEvent event) throws IOException {

        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/sample/gui/view/newCategory.fxml")); // Path til FXML filen der tilhøre scenen der skal vises
        Scene mainWindowScene = new Scene(mainWindowParent); //Scenen der skal vises
        Stage newSongStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        newSongStage.setScene(mainWindowScene); // Sætter nuværende scene
        newSongStage.show(); // Viser Scenen som lige er blevet sat ovenover
    }

    public void handleSearch(javafx.scene.input.KeyEvent keyEvent) throws IOException {
        if (filter.getText() == null || filter.getText().length() <= 0) {
            tableViewMovies.setItems(movieModel.getMovies());
        } else {
            ObservableList<Movie> foundMovieList = movieModel.filter(movieModel.getMovies(), filter.getText());

            tableViewMovies.setItems(foundMovieList);

        }
    }
}
