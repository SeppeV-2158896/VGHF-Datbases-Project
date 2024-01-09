package be.vghf.controllers;

import be.vghf.domain.Location;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.LocationRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SelectLocationController implements Controller{
    @FXML
    private TextField addressField;
    @FXML
    private TableView<Location> table;
    @FXML
    private Button addButton;
    @FXML
    private Button confirmButton;
    private BaseController baseController;
    private Controller listener;
    private boolean homeBase;
    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {
        this.listener = controller;
    }

    public void setIsHomeBase(boolean isHomeBase){
        this.homeBase = isHomeBase;
    }

    @FXML protected void initialize(){
        TableColumn<Location, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> {
            String address = cellData.getValue().toString();
            return new SimpleStringProperty(address != null ? address : "");
        });

        TableColumn<Location, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("locationType"));

        TableColumn<Location, String> ownerColumn = new TableColumn<>("Owner");
        ownerColumn.setCellValueFactory(cellData -> {
            String owner = cellData.getValue().getOwner().getFirstName() + " " + cellData.getValue().getOwner().getLastName();
            return new SimpleStringProperty(owner != null ? owner : "");
        });

        table.getColumns().addAll(addressColumn, typeColumn, ownerColumn);

        var results = new LocationRepository().getAll();
        table.setItems(FXCollections.observableArrayList(results));

        confirmButton.setDisable(true);
    }

    @FXML protected void handleSearch(KeyEvent event){
        if(event.getCode() != KeyCode.ENTER){
            return;
        }

        String addressText = addressField.getText();
        String[] addressArray = addressText.split("\\s+");

        LocationRepository locationRepository = new LocationRepository();
        List<Location> results = new ArrayList<>();
        if(!addressText.isEmpty()){
            results.addAll(locationRepository.getLocationByAddress(addressArray));
        }
        else{
            results.addAll(locationRepository.getAll());
        }
        table.setItems(FXCollections.observableArrayList(results));
    }

    @FXML protected void handleMouseClick(MouseEvent event){
        if(event.getClickCount() == 1 && table.getSelectionModel().getSelectedItem() != null){
            confirmButton.setDisable(false);
        }
        else{
            confirmButton.setDisable(true);
        }
    }

    @FXML protected void addLocation(ActionEvent event) {
        CreateLocationController createLocationController = new CreateLocationController();
        createLocationController.setListener(this);
        baseController.showView("Create new location", createLocationController, "/createLocation-view.fxml");
    }

    public void newLocationCreated(Location location){
        GenericRepository.save(location);

        table.getItems().add(location);
    }
    @FXML protected void confirmSelectedLocation(ActionEvent event){
        Location selectedLocation = table.getSelectionModel().getSelectedItem();
        if (selectedLocation != null && listener != null) {
            if(listener instanceof GameAdminController){
                if(homeBase){
                    ((GameAdminController) listener).selectedHomeBaseConfirmed(selectedLocation);
                }
                else{
                    ((GameAdminController) listener).selectedCurrentLocationConfirmed(selectedLocation);
                }

            }

            Button sourceButton = (Button) event.getSource();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.close();
        }
    }
}
