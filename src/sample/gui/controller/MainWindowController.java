package sample.gui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.be.Category;
import sample.be.Movie;
import sample.gui.model.CategoryModel;
import sample.gui.model.MovieModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public TableView<Movie> tableViewMovies;
    public TableColumn<String, Movie> movieTitleCol;
    public TableColumn<Double, Movie> moviePersRatingCol;
    public TableView<Category> tableViewCategory;
    public TableColumn<String, Category> categoryGenreCol;
    public TableColumn movieIMDbRatingCol;
    public TableColumn<Date, Movie> movieLastViewCol;
    private MovieModel movieModel;
    public Button closeButton;

    private ObservableList observableListMovies;
    private CategoryModel categoryModel;
    private ObservableList observableListCategories;
    private String moviePath = "Movies/";


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


        try {
            observableListCategories = categoryModel.getCategories();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        categoryGenreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tableViewCategory.setItems(observableListCategories);
        movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        moviePersRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        movieLastViewCol.setCellValueFactory(new PropertyValueFactory<>("lastview"));
        tableViewMovies.setItems(observableListMovies);

        tableViewCategory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Category category = newSelection;
                try {
                    ObservableList movies = movieModel.getCategoryMovies(category.getCategoryId());
                    movieTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                    moviePersRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
                    movieLastViewCol.setCellValueFactory(new PropertyValueFactory<>("lastview"));
                    tableViewMovies.setItems(movies);
                    tableViewMovies.getSelectionModel().selectFirst();

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void handleClose(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    public void handleSearch(javafx.scene.input.KeyEvent keyEvent) throws IOException, SQLException {
        String searchParam = keyEvent.getText();
        if (searchParam == null || searchParam.length() <= 0) {
            Category selectedCategory = tableViewCategory.getSelectionModel().getSelectedItem();
            if(selectedCategory != null){
                tableViewMovies.setItems(movieModel.getCategoryMovies(selectedCategory.getCategoryId()));

            }
            else {
                tableViewMovies.setItems(movieModel.getMovies());
            }
        } else {
            ObservableList<Movie> foundMovieList = movieModel.filter(movieModel.getMovies(), searchParam);
            tableViewMovies.setItems(foundMovieList);
        }
    }

    public void clickNewCategory(javafx.event.ActionEvent event) throws IOException {
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/sample/gui/view/newCategory.fxml")); // Path til FXML filen der tilhører scenen der skal vises
        Scene mainWindowScene = new Scene(mainWindowParent); //Scenen der skal vises
        Stage newSongStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        newSongStage.setScene(mainWindowScene); // Sætter nuværende scene
        newSongStage.show(); // Viser Scenen som lige er blevet sat ovenover
        refreshCategoryList();
    }

    public void handleAddNewMovie(ActionEvent actionEvent) throws IOException, SQLException {
        Category selectedCategory =  tableViewCategory.getSelectionModel().getSelectedItem();
        if(selectedCategory == null)
            {
                error("Please select category");
            }
        FXMLLoader  loader = new FXMLLoader(getClass().getResource("/sample/gui/view/NewMovie.fxml")); // Path til FXML filen der tilhører scenen der skal vises
        Parent mainWindowParent = loader.load();
        //sæt category i new movie window til valgte categoey
        NewMovieController controller = loader.getController();
        controller.setCategory(selectedCategory);
        Scene mainWindowScene = new Scene(mainWindowParent); //Scenen der skal vises
        Stage newMovieStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newMovieStage.setScene(mainWindowScene); // Sætter nuværende scene
        newMovieStage.show(); // Viser Scenen som lige er blevet sat ovenover
        refreshMovieCategories();

    }


    public void handleRemoveMovie(ActionEvent actionEvent) throws SQLException {
        Movie movie = tableViewMovies.getSelectionModel().getSelectedItem();
        Category category = tableViewCategory.getSelectionModel().getSelectedItem();
        if(category == null){
            error("Please select category");
        }
        try {
            if(movie != null){
                tableViewMovies.getItems().remove(movie);
                categoryModel.deleteMovieFromCategory(category.getCategoryId(), movie.getId());
            }
        } catch (Exception exception) {
            error("Please select movie");
            exception.printStackTrace();
        }
    }
    private void error(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.YES);
        alert.showAndWait();
    }

    public void refreshMovieCategories() throws IOException, SQLException {
        tableViewMovies.getItems().clear();
        Category selectedCategory = tableViewCategory.getSelectionModel().getSelectedItem();
        tableViewMovies.setItems(movieModel.getCategoryMovies(selectedCategory.getCategoryId()));
    }


    public void refreshCategoryList() throws IOException {
        tableViewCategory.getItems().clear();
        tableViewCategory.setItems(categoryModel.getCategories());
    }

    public void removeCategory(ActionEvent actionEvent) {
        Category category = tableViewCategory.getSelectionModel().getSelectedItem();
        if(category == null)
        {
            error("Please select a category");
        }
        tableViewCategory.getItems().remove(category);
            if(category != null)
            {
                try {
                    categoryModel.removeCategory(category);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
    }

    public void handlOpenMovie(ActionEvent actionEvent) throws IOException {
        Movie movie = tableViewMovies.getSelectionModel().getSelectedItem();
        if(movie == null)
        {
            error("Please select movie");
        }
        Desktop.getDesktop().open(new File(moviePath + movie.getUrl()));
    }


    public void handleEditSong(ActionEvent actionEvent) throws IOException {
        Movie movie = tableViewMovies.getSelectionModel().getSelectedItem(); //Vælger objektet der skal ændres
        FXMLLoader mainWindowParent =  new FXMLLoader(getClass().getResource("/sample/gui/view/newMovieEdit.fxml"));
        Scene mainWindowScene = null; //Scenen der skal vises
        mainWindowScene = new Scene(mainWindowParent.load());
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene); // Sætter nuværende scene
        NewMovieEditController newMovieEditController= mainWindowParent.getController();
        newMovieEditController.setMovie(movie);
        editSongStage.show(); // Viser Scenen som lige er blevet sat ovenover
    }
}
