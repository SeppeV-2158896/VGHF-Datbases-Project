package be.vghf.controllers;

import be.vghf.domain.Location;
import be.vghf.enums.LocationType;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.LocationRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EventsController implements Controller {
    private BaseController baseController;
    @FXML
    private TilePane locationsTilePane;


    @FXML public void initialize(){
        setLocations(new LocationRepository().getAll());
    }

    public void setLocations(List<Location> locations) {
        locationsTilePane.getChildren().addAll(
                locations.stream()
                        .filter(location -> {
                            if (ActiveUser.user != null && ActiveUser.user.getUserType() == UserType.VOLUNTEER) {
                                return true;
                            } else {
                                return location.getLocationType() != LocationType.PRIVATE &&
                                        location.getLocationType() != LocationType.STORAGE;
                            }
                        })
                        .map(this::createLocationTile)
                        .collect(Collectors.toList())
        );
    }

    private AnchorPane createLocationTile(Location location) {
        AnchorPane anchorPane = new AnchorPane();

        Label ownerLabel = new Label("Owner: " + location.getOwner().getFirstName() + " " + location.getOwner().getLastName());
        ownerLabel.setWrapText(true);

        Label addressLabel = new Label("Address: " + location);
        addressLabel.setWrapText(true);

        Label typeLabel = new Label("Type: " + location.getLocationType());
        typeLabel.setWrapText(true);

        Hyperlink gamesLink = new Hyperlink("View Games");
        gamesLink.setOnAction(event -> handleGames(location));

        Hyperlink controlPanelLink = new Hyperlink("Open Control Panel");
        controlPanelLink.setOnAction(event -> handleControl(location));
        controlPanelLink.setVisible(ActiveUser.user != null && ActiveUser.user.getUserType() == UserType.VOLUNTEER);

        anchorPane.getChildren().addAll(ownerLabel, addressLabel, typeLabel, gamesLink, controlPanelLink);

        AnchorPane.setTopAnchor(ownerLabel, 10.0);
        AnchorPane.setLeftAnchor(ownerLabel, 10.0);
        AnchorPane.setTopAnchor(addressLabel, 30.0);
        AnchorPane.setLeftAnchor(addressLabel, 10.0);
        AnchorPane.setTopAnchor(typeLabel, 50.0);
        AnchorPane.setLeftAnchor(typeLabel, 10.0);
        AnchorPane.setTopAnchor(gamesLink, 70.0);
        AnchorPane.setLeftAnchor(gamesLink, 10.0);
        AnchorPane.setTopAnchor(controlPanelLink, 90.0);
        AnchorPane.setLeftAnchor(controlPanelLink, 10.0);


        return anchorPane;
    }

    private void handleControl(Location location) {
        var controller = new ControlPannelController();
        baseController.showView("Location Control Panel", controller, "/controlPanel-view.fxml");
        controller.setLocation(location);
    }

    private void handleGames(Location location) {
        // Code to open a screen with games available at the location
        // This functionality might involve creating a new controller/view to display games
        // and linking it to this button action

            if (ActiveUser.user == null || ActiveUser.user.getUserType() == UserType.CUSTOMER){
                BrowseController bController = new BrowseController();
                baseController.changeSubscene("/browse-view.fxml", bController);
                bController.initializeGamesWithLocation(location);
            }else if (ActiveUser.user.getUserType() == UserType.VOLUNTEER){
                var controller = new GamesAtLocationController();
                baseController.changeSubscene("/location-games-tree-view.fxml", controller);
                controller.initialize(location);
            }
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
        baseController.setListener(this);
    }

    @Override
    public void setListener(Controller controller) {

    }
}
