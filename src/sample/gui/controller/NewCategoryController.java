package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.bll.MyMovieManager;
import sample.gui.model.CategoryModel;

import java.io.IOException;
import java.sql.SQLException;

public class NewCategoryController {

    public TextField txtFieldCategory;
    private MyMovieManager myMovieManager;
    private CategoryModel categoryModel;
    private MainWindowController mainWindowController;

    public NewCategoryController() throws IOException {
        myMovieManager = new MyMovieManager();
        categoryModel = new CategoryModel();
        mainWindowController = new MainWindowController();

    }

    public void cancelCategory(javafx.event.ActionEvent event) throws IOException {
        Parent newCategoryWindow = FXMLLoader.load(getClass().getResource("/sample/gui/view/MainWindow.fxml")); // Path til fxml filen der tilhøre scenen som skal vises ved tryk på knap
        Scene newSongScene = new Scene(newCategoryWindow); // Ny scene som skal vises oprettes
        Stage mainWindowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindowStage.setScene(newSongScene); // Sætter den nye scene
        mainWindowStage.show(); // Viser den nye scene
    }


    public void handleCreateCategory(ActionEvent actionEvent) throws SQLException, IOException {
        categoryModel.createCategory(txtFieldCategory.getText());
        mainWindowController.refreshCategoryList();
        cancelCategory(actionEvent);
    }

}
