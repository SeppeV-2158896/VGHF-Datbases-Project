package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.enums.LocationType;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.GameRepository;
import be.vghf.repository.LocationRepository;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class EventsController implements Controller {
    private BaseController baseController;
    private LocationRepository locationRepository;
    @FXML
    private TilePane locationsTilePane;

    @FXML private TextField locationSearchText;
    @FXML private ComboBox<String> locationTypeComboBox;

    @FXML public void initialize(){
        locationRepository = new LocationRepository();
        setLocations(locationRepository.getAll());

        locationTypeComboBox.setPromptText("Location type");
        locationTypeComboBox.getItems().add("All");
        locationTypeComboBox.getItems().add("Expo");
        locationTypeComboBox.getItems().add("Library");
        locationTypeComboBox.getItems().add("Museum");
        if (ActiveUser.user != null && ActiveUser.user.getUserType() == UserType.VOLUNTEER) {
            locationTypeComboBox.getItems().add("Private");
            locationTypeComboBox.getItems().add("Storage");
        }

        locationTypeComboBox.setOnAction(this::handleLocationTypeChanged);
        locationSearchText.setOnKeyReleased(this::handleLocationSearch);

    }

    @FXML protected void handleLocationTypeChanged(ActionEvent actionEvent) {
        String locationType = locationTypeComboBox.getValue();
        List<Location> locationResults = null;

        if(this.locationSearchText.getText() != ""){
            String locationSearchText = this.locationSearchText.getText();
            String[] locationSearch = locationSearchText.split("\\s+");
            if(locationType == "Private"){
                locationResults = queryPrivateLocations(locationSearch);
            }else if(locationType == "Expo"){
                locationResults = queryExpoLocations(locationSearch);
            }else if(locationType == "Library"){
                locationResults = queryLibraryLocations(locationSearch);
            }else if(locationType == "Storage"){
                locationResults = queryStorageLocations(locationSearch);
            }else if(locationType == "museum"){
                locationResults = queryMuseumLocations(locationSearch);

            }else{
                locationResults = queryLocationsWithoutType(locationSearch);
            }
            setLocations(locationResults);
            return;
        }

        if(locationType == "Private"){
            locationResults = locationRepository.getLocationByType(LocationType.PRIVATE, locationRepository.getAll());
        }else if(locationType == "Expo"){
            locationResults = locationRepository.getLocationByType(LocationType.EXPO, locationRepository.getAll());
        }else if(locationType == "Library"){
            locationResults = locationRepository.getLocationByType(LocationType.LIBRARY, locationRepository.getAll());
        }else if(locationType == "Storage") {
            locationResults = locationRepository.getLocationByType(LocationType.STORAGE, locationRepository.getAll());
        }else if(locationType == "Museum") {
            locationResults = locationRepository.getLocationByType(LocationType.MUSEUM, locationRepository.getAll());
        }else{
            locationResults = locationRepository.getAll();
        }
        setLocations(locationResults);
    }

    @FXML protected void handleLocationSearch(KeyEvent event) {
        if(event.getCode() != KeyCode.ENTER){
            return;
        }

        String locationType = locationTypeComboBox.getValue();

        String locationSearchText = this.locationSearchText.getText();
        String[] locationSearch = locationSearchText.split("\\s+");
        List<Location> locationResults = null;
        if(locationType == "Private"){
            locationResults = queryPrivateLocations(locationSearch);
        }else if(locationType == "Expo"){
            locationResults = queryExpoLocations(locationSearch);
        }else if(locationType == "Library"){
            locationResults = queryLibraryLocations(locationSearch);
        }else if(locationType == "Storage"){
            locationResults = queryStorageLocations(locationSearch);
        }else if(locationType == "Museum"){
            locationResults = queryMuseumLocations(locationSearch);
        }else{
            locationResults = queryLocationsWithoutType(locationSearch);
        }
        setLocations(locationResults);
    }

    private List<Location> queryLocationsWithoutType(String[] locationSearch) {
        var results = locationRepository.getLocationByAddress(locationSearch);
        return results;
    }
    private List<Location> queryMuseumLocations(String[] locationSearch) {
        var addressResults = locationRepository.getLocationByAddress(locationSearch);
        var results = locationRepository.getLocationByType(LocationType.MUSEUM, addressResults);
        return results;
    }

    private List<Location> queryStorageLocations(String[] locationSearch) {
        var addressResults = locationRepository.getLocationByAddress(locationSearch);
        var results = locationRepository.getLocationByType(LocationType.STORAGE, addressResults);
        return results;
    }

    private List<Location> queryLibraryLocations(String[] locationSearch) {
        var addressResults = locationRepository.getLocationByAddress(locationSearch);
        var results = locationRepository.getLocationByType(LocationType.LIBRARY, addressResults);
        return results;
    }

    private List<Location> queryExpoLocations(String[] locationSearch) {
        var addressResults = locationRepository.getLocationByAddress(locationSearch);
        var results = locationRepository.getLocationByType(LocationType.EXPO, addressResults);
        return results;
    }

    private List<Location> queryPrivateLocations(String[] locationSearch) {
        var addressResults = locationRepository.getLocationByAddress(locationSearch);
        var results = locationRepository.getLocationByType(LocationType.PRIVATE, addressResults);
        return results;    }

    public void setLocations(List<Location> locations) {
        locationsTilePane.getChildren().clear();
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
            if (ActiveUser.user == null || ActiveUser.user.getUserType() == UserType.CUSTOMER){
                BrowseController bController = new BrowseController();
                baseController.changeSubscene("/browse-view.fxml", bController);
                bController.initializeGamesWithLocation(location);
            }else if (ActiveUser.user.getUserType() == UserType.VOLUNTEER){
                var controller = new GamesAtLocationController();
                baseController.changeSubscene("/location-games-tree-view.fxml", controller);
                controller.initialize(location);
                controller.setBaseController(baseController);
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
