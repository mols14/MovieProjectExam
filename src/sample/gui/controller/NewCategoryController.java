package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public NewCategoryController() throws IOException {
        myMovieManager = new MyMovieManager();
        categoryModel = new CategoryModel();

    }
    /*
        cancel metode til at ændre tilbage til man scene
     */
    public void cancelCategory(javafx.event.ActionEvent event) throws IOException {
        Parent newCategoryWindow = FXMLLoader.load(getClass().getResource("/sample/gui/view/MainWindow.fxml")); // Path til fxml filen der tilhøre scenen som skal vises ved tryk på knap
        Scene newMovieScene = new Scene(newCategoryWindow); // Ny scene som skal vises oprettes
        Stage mainWindowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindowStage.setScene(newMovieScene); // Sætter den nye scene
        mainWindowStage.show(); // Viser den nye scene
    }


    /*
        Anvender createCategory fra categoryModel til at lave en ny kategory
     */
    public void handleCreateCategory(ActionEvent actionEvent) throws SQLException, IOException {
            if( txtFieldCategory.getLength() ==0 ) {
                error("Please fill out all fields");
            }
            else {
                categoryModel.createCategory(txtFieldCategory.getText());
                cancelCategory(actionEvent);
            }
        }

        /*
            error meddelse metode
         */
    private void error(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.YES);
        alert.showAndWait();
    }

}
