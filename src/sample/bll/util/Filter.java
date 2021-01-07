package sample.bll.util;

import sample.be.Movie;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    public List<Movie> filter(List<Movie> filterBase, String query) {

        List<Movie> filterResult = new ArrayList<>();
        for (Movie movie : filterBase) {
            if(compareToMovieTitle(query, movie) || compareToMovieCategory)
        }
    }

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieCategory(String query, Movie movie) {
        return category.getCategory().toLowerCase().contains(query.toLowerCase());
    }

}
