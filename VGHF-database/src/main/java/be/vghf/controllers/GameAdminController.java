package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.repository.GenericRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GameAdminController{

    @FXML
    private TextField titleField;

    @FXML
    private TextField releaseDateField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField ownerField;

    @FXML
    private TextField homeBaseField;

    @FXML
    private TextField currentLocationField;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    // Inject your Game instance here
    private Game game;

    // Set the Game instance from your main application
    public void setGame(Game game) {
        this.game = game;
        populateFields();
    }
    public void saveGame(ActionEvent actionEvent) {
        game.setTitle(titleField.getText());
        game.setReleaseDate(releaseDateField.getText());
        game.setGenre(genreField.getText());

        GenericRepository.update(game);
    }

    public void deleteGame(ActionEvent actionEvent) {
        GenericRepository.delete(game);
    }

    private void populateFields() {
        titleField.setText(game.getTitle());
        releaseDateField.setText(game.getReleaseDate());
        genreField.setText(game.getGenre());
    }

}
