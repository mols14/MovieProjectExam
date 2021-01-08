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

    public ObservableList<Category> getCategories() throws IOException {
       allCategories = FXCollections.observableArrayList();
       allCategories.addAll(myMovieManager.getAllCategories());
        return allCategories;
    }
    public void createCategory(String name) throws SQLException {
        myMovieManager.createCategory(name);
    }
}

