package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.bll.MyMovieManager;

import java.io.IOException;
import java.sql.SQLException;

public class NewCategoryController {

    public TextField txtFieldCategory;
    private MyMovieManager myMovieManager;

    public NewCategoryController() throws IOException {
        myMovieManager = new MyMovieManager();
    }

    public void cancelCategory(javafx.event.ActionEvent event) throws IOException {
        Parent newSongWindow = FXMLLoader.load(getClass().getResource("/sample/gui/view/MainWindow.fxml")); // Path til fxml filen der tilhøre scenen som skal vises ved tryk på knap
        Scene newSongScene = new Scene(newSongWindow); // Ny scene som skal vises oprettes
        Stage mainWindowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindowStage.setScene(newSongScene); // Sætter den nye scene
        mainWindowStage.show(); // Viser den nye scene
    }


    public void handleCreateCategory(ActionEvent actionEvent) throws SQLException, IOException {
        myMovieManager.createCategory(txtFieldCategory.getText());
        cancelCategory(actionEvent);
    }

}
