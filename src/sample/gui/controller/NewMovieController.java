package sample.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewMovieController {


    public void handleNewMovieCancel(ActionEvent actionEvent) throws IOException {
        Parent newMovieWindow = FXMLLoader.load(getClass().getResource("/sample/gui/view/MainWindow.fxml")); // Path til fxml filen der tilhøre scenen som skal vises ved tryk på knap
        Scene newSongScene = new Scene(newMovieWindow); // Ny scene som skal vises oprettes
        Stage mainWindowStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainWindowStage.setScene(newSongScene); // Sætter den nye scene
        mainWindowStage.show(); // Viser den nye scene
    }

    public void handleNewMovieDone(ActionEvent actionEvent) {
    }
}
