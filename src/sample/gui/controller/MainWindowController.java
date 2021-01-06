package sample.gui.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

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
}
