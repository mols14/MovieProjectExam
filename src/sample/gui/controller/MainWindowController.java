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
import sample.be.Category;
import sample.be.Movie;
import sample.gui.model.CategoryModel;
import sample.gui.model.MovieModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController {

    public TableView<Movie> tableViewMovies;
    public TableColumn<String, Movie> movieTitleCol;
    public TextField filter;
    public TableColumn<String, Category> categoryGenreCol;
    public TableView<Category> tableViewCategory;
    private MovieModel movieModel;
    public Button closeButton;

    private ObservableList observableListMovies;
    private CategoryModel categoryModel;
    private ObservableList observableListCategories;

    public MainWindowController() throws IOException {
        tableViewMovies = new TableView<>();
        tableViewCategory = new TableView<>();
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
             observableListMovies = movieModel.getMovies();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        try {
            observableListCategories = categoryModel.getCategories();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        categoryGenreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableViewCategory.setItems(observableListCategories);
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

    public void handleAddNewMovie(ActionEvent actionEvent) throws IOException {
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/sample/gui/view/NewMovie.fxml")); // Path til FXML filen der tilhøre scenen der skal vises
        Scene mainWindowScene = new Scene(mainWindowParent); //Scenen der skal vises
        Stage newMovieStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newMovieStage.setScene(mainWindowScene); // Sætter nuværende scene
        newMovieStage.show(); // Viser Scenen som lige er blevet sat ovenover

    }

    public void handleRemoveMovie(ActionEvent actionEvent) {
    }

    public void refreshCategoryList() throws IOException {
        tableViewCategory.getItems().clear();
        tableViewCategory.setItems(categoryModel.getCategories());
    }
}
