package sample.bll;

import sample.be.Category;
import sample.be.Movie;
import sample.dal.CategoryDAO;
import sample.dal.MovieDAO;

import java.sql.SQLException;
import java.util.Date;
import java.io.IOException;
import java.util.List;

public class MyMovieManager {
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;

    public MyMovieManager() throws IOException {
        categoryDAO = new CategoryDAO();
        movieDAO = new MovieDAO();
    }

    public Movie createMovie(String title, double rating, String url, Date lastview) throws SQLException {
        return movieDAO.createMovie(title, rating, url, lastview);
    }

    public Category createCategory(String name) throws SQLException {
        return categoryDAO.createCategory(name);
    }

    public List<Movie> getAllMovies() throws IOException {
        return MovieDAO.getAllMovies();
    }
}
