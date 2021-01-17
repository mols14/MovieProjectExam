package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.MyMovieManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MovieModel {

    private MyMovieManager myMovieManager;
    private ObservableList<Movie> allMovies = FXCollections.observableArrayList();

    public MovieModel() throws IOException {
        myMovieManager = new MyMovieManager();
    }

    public ObservableList<Movie> getMovies() throws IOException {
        allMovies = FXCollections.observableArrayList();
        allMovies.addAll(myMovieManager.getAllMovies());
        return allMovies;
    }

    public Movie createMovie(String title, double rating, String url, Date lastview) throws SQLException {
        return myMovieManager.createMovie(title, rating, url, lastview);
    }

    public ObservableList<Movie> filter(List<Movie> searchBase, String query) {
        return myMovieManager.filter((ObservableList<Movie>) searchBase, query);
    }

    public ObservableList<Movie> getCategoryMovies(int categoryId) throws IOException, SQLException {
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.addAll(myMovieManager.getCategoryMovies(categoryId));
        return movies;
    }


}
