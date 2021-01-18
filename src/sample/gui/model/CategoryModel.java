package sample.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.be.Category;
import sample.bll.MyMovieManager;

import java.io.IOException;
import java.sql.SQLException;

    public class CategoryModel {
    private MyMovieManager myMovieManager;
    private ObservableList <Category> allCategories = FXCollections.observableArrayList();

    public CategoryModel() throws IOException {
    myMovieManager  = new MyMovieManager();
}
    /*
      Opretter ObservableList med kategory objekter
      således at den kan opdateres løbende. Henter alle kategorier fra manageren
     */
    public ObservableList<Category> getCategories() throws IOException {
       allCategories = FXCollections.observableArrayList();
       allCategories.addAll(myMovieManager.getAllCategories());
       return allCategories;
    }

    public void createCategory(String genre) throws SQLException {
        myMovieManager.createCategory(genre);
    }

    public void removeCategory(Category deleteCategory) throws SQLException {
        myMovieManager.deleteCategory(deleteCategory);
    }

    public void addMovieToCategory (int categoryId, int movieId)
    {
        myMovieManager.addMovieToCategory(categoryId, movieId);
    }
    public void deleteMovieFromCategory(int CategoryId, int MovieId){
        myMovieManager.deleteMovieFromCategory(CategoryId, MovieId);
    }

}

