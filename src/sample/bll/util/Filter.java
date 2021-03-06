package sample.bll.util;

import sample.be.Movie;
import sample.be.Category;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    /*
        Vi opretter i metoden filter en arraylist af movies, hvor i vi anvender
        et for-loop til at køre igennem filterbasen (liste af film)
        Der compares efter filmens titel og rating
    */
    public List<Movie> filter(List<Movie> filterBase, String query) {

        List<Movie> filterResult = new ArrayList<>();
        for (Movie movie  : filterBase) {
            if (compareToMovieTitle(query, movie) || (compareToMovieRating(query, movie))) {
                filterResult.add(movie);
            }
        }
        return filterResult;
    }

    /*
    filtercat metoden anvender sammee logik som filter metoden
    med den forskel at det er en arraylist med categorier der bliver søgt efter, i stedet for sange
     */
    public List<Category> filterCat(List<Category> filterBaseCat, String query){

        List<Category> filterResultCat = new ArrayList<>();
        for(Category category : filterBaseCat) {
            if (compareToMovieCategory(query, category)) {
                filterResultCat.add(category);
            }
        }
        return filterResultCat;

    }

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieCategory(String query, Category category) {
        return category.getGenre().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieRating(String query, Movie movie)
    {
        return Double.toString(movie.getRating()).toLowerCase().contains(query.toLowerCase());
    }

}
