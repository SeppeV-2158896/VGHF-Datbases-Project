package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.repository.GenericRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GameAdminController implements Controller {
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
    @FXML
    private Label windowTitle;

    private BrowseController listener;

    // Inject your Game instance here
    private Game game;
    private boolean newGame = false;

    @FXML protected void initialize(){
        if(!newGame){
            populateFields();
            windowTitle.setText("Game details");
        }
        else{
            game = new Game();
        }
    }

    // Set the Game instance from your main application
    public void setGame(Game game) {
        this.game = game;
    }

    public void setNewGame(boolean isNewGame){
        this.newGame = isNewGame;
    }
    public void saveGame(ActionEvent event) {
        game.setTitle(titleField.getText());
        game.setReleaseDate(releaseDateField.getText());
        game.setGenre(genreField.getText());

        if(!newGame){
            GenericRepository.update(game);
        }
        else{
            GenericRepository.save(game);
        }

        listener.updateGameDetails(game);

        Button sourceButton = (Button) event.getSource();
        Stage stage = (Stage) sourceButton.getScene().getWindow();
        stage.close();
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
        EditGameOwnerController egoController = new EditGameOwnerController();
        egoController.setListener(this);
        baseController.showView("Edit owner", egoController, "/editGameOwner-view.fxml");
    }

    public void selectedUserConfirmed(User user){
        game.setOwner(user);
        ownerButton.setText(game.getOwner().getFirstName() + " " + game.getOwner().getLastName());
    }

    @FXML protected void editLocation(ActionEvent event){
        EditGameLocationController eglController = new EditGameLocationController();
        eglController.setListener(this);
        String title = "";
        if(event.getSource() == homeBaseButton){
            title = "Edit home base location";
            eglController.setIsHomeBase(true);
        }
        else if(event.getSource() == currentLocationButton){
            title = "Edit current location";
            eglController.setIsHomeBase(false);
        }
        baseController.showView(title, eglController, "/editGameLocation-view.fxml");
    }

    public void selectedHomeBaseConfirmed(Location location){
        game.setHomeBase(location);
        homeBaseButton.setText(game.getHomeBase().toString());
    }

    public void selectedCurrentLocationConfirmed(Location location){
        game.setCurrentLocation(location);
        currentLocationButton.setText(game.getCurrentLocation().toString());
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller){
        this.listener = (BrowseController) controller;
    }
}
