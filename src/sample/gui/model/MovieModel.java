package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Movie;
import sample.bll.MyMovieManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

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

    public void createMovie(String title, double rating, String url, Date lastview) throws SQLException {
        myMovieManager.createMovie(title, rating, url, lastview);
    }

}
