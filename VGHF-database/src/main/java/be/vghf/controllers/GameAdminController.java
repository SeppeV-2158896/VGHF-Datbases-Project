package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.repository.GenericRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GameAdminController implements Controller{
    private BaseController baseController;

    @FXML
    private TextField titleField;

    @FXML
    private TextField releaseDateField;

    @FXML
    private TextField genreField;

    @FXML
    private Button ownerButton;

    @FXML
    private Button homeBaseButton;

    @FXML
    private Button currentLocationButton;

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
        ownerButton.setText(game.getOwner().getFirstName() + " " + game.getOwner().getLastName());
        homeBaseButton.setText(game.getHomeBase().toString());
        currentLocationButton.setText(game.getCurrentLocation().toString());
    }

    @FXML protected void editOwner(ActionEvent event){
        try{ //nog aanpassen anders gaat het nie werken he
            baseController.createNewWindow("Edit owner", new EditOwnerLocationController(), "/editOwnerLocations-view.fxml");
        } catch (IOException e){
            throw new RuntimeException("Failed to open window: " + e.getMessage(), e);
        }
    }

    public void editHomeBase(ActionEvent event){

    }

    public void editCurrentLocation(ActionEvent event){

    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }
}
