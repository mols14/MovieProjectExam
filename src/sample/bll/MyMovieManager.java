package sample.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Category;
import sample.be.Movie;
import sample.bll.util.Filter;
import sample.dal.CategoryDAO;
import sample.dal.MovieDAO;

import java.sql.SQLException;
import java.util.Date;
import java.io.IOException;
import java.util.List;

public class MyMovieManager {
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;
    private Filter filter;

    public MyMovieManager() throws IOException {
        categoryDAO = new CategoryDAO();
        movieDAO = new MovieDAO();
        filter = new Filter();
    }

    public List<Category> getAllCategories() throws IOException {
        return categoryDAO.getAllCategories();
    }

    public Movie createMovie(String title, double rating, String url, Date lastview) throws SQLException {
        return movieDAO.createMovie(title, rating, url, lastview);
    }

    public Category createCategory(String name) throws SQLException {
        return categoryDAO.createCategory(name);
    }

    public List<Movie> getAllMovies() throws IOException {
        return movieDAO.getAllMovies();
    }

    public ObservableList<Movie> filter(ObservableList<Movie> filterbase, String query) {
        ObservableList<Movie> foundMovies = FXCollections.observableArrayList();
        foundMovies.addAll(filter.filter(filterbase, query));
        return foundMovies;
    }

}
