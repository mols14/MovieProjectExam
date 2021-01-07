package sample.gui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class MainWindowController implements initializable {



    public Button closeButton;

    public void handleSearch(KeyEvent keyEvent) throws IOException {
        if (filterSearch.getText() == null || filterSearch.getText().length() <= 0) {
            tableViewSongs.setItems(songModel.getSongs());
        } else {
            ObservableList<Song> foundMovieList = songModel.filter(songModel.getSongs(), filterSearch.getText());

            tableViewSongs.setItems(foundMovieList);

        }
    }

    public void handleClose(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void clickNewCategory(ActionEvent actionEvent) {

        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/GUI/View/NewSong.fxml")); // Path til FXML filen der tilhøre scenen der skal vises
        Scene mainWindowScene = new Scene(mainWindowParent); //Scenen der skal vises
        Stage newSongStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        newSongStage.setScene(mainWindowScene); // Sætter nuværende scene
        newSongStage.show(); // Viser Scenen som lige er blevet sat ovenover
    }
}
