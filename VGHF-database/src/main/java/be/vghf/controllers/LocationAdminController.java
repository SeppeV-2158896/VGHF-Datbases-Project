package be.vghf.controllers;

import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.enums.LocationType;
import be.vghf.repository.GameRepository;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.Loan_ReceiptsRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class LocationAdminController implements Controller {
    private BaseController baseController;

    @FXML
    private TextField streetField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField busField;
    @FXML
    private TextField postalcodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private Button ownerButton;
    @FXML
    private ComboBox<LocationType> locationTypeComboBox;
    @FXML
    private Button saveButton;

    @FXML
    private Label windowTitle;

    private Controller listener;

    // Inject your Location instance here
    private Location location;
    private boolean newLocation = false;

    @FXML protected void initialize(){
        locationTypeComboBox.getItems().add(LocationType.EXPO);
        locationTypeComboBox.getItems().add(LocationType.LIBRARY);
        locationTypeComboBox.getItems().add(LocationType.STORAGE);
        locationTypeComboBox.getItems().add(LocationType.MUSEUM);
        locationTypeComboBox.getItems().add(LocationType.PRIVATE);
        if(!newLocation){
            windowTitle.setText("Location details");
        }
        else{
            location = new Location();
        }
    }

    public void setLocation(Location location) {
        this.location = location;
        populateFields();
    }

    public void setNewLocation(boolean isNewLocation){
        this.newLocation = isNewLocation;
    }
    public void saveLocation(ActionEvent event) {
         if (areFieldsEmpty()){
            BaseController.showErrorAlert("Error", "All fields with a * should be filled in!");
            return;
        }
        if (isTypeNotChosen()){
            BaseController.showErrorAlert("Error", "Location type has to be chosen!");
            return;
        }
        if (isOwnerNotChosen()){
            BaseController.showErrorAlert("Error", "Owner has to be chosen!");
            return;
        }

        location.setStreetName(streetField.getText());
        if(validHouseNumber()){
            location.setHouseNumber(Integer.valueOf(numberField.getText()));
        }else{
            BaseController.showErrorAlert("Error", "The house number must be a number");
            return;
        }
        location.setBus(busField.getText());
        location.setPostalCode(postalcodeField.getText());
        location.setCity(cityField.getText());
        location.setCountry(countryField.getText());
        location.setLocationType(locationTypeComboBox.getValue());


        if(!newLocation){
            GenericRepository.update(location);
        }
        else{
            GenericRepository.save(location);
        }

        ((EventsController) listener).updateLocationDetails(location);

        Button sourceButton = (Button) event.getSource();
        Stage stage = (Stage) sourceButton.getScene().getWindow();
        stage.close();
    }

    private void populateFields() {
        streetField.setText(location.getStreetName());
        numberField.setText(String.valueOf((location.getHouseNumber())));

        if(location.getBus() != null){busField.setText(location.getBus());}
        postalcodeField.setText(location.getPostalCode());
        cityField.setText(location.getCity());
        countryField.setText(location.getCountry());

        ownerButton.setText(location.getOwner().getFirstName() + " " + location.getOwner().getLastName());
        locationTypeComboBox.setValue(location.getLocationType());
    }

    @FXML protected void editOwner(ActionEvent event){
        SelectuserController egoController = new SelectuserController();
        egoController.setListener(this);
        baseController.showView("Edit owner", egoController, "/selectUser-view.fxml");
    }

    public void selectedUserConfirmed(User user){
        location.setOwner(user);
        ownerButton.setText(location.getOwner().getFirstName() + " " + location.getOwner().getLastName());
    }

    public void closeWindow(){
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private boolean areFieldsEmpty() {
        return  streetField.getText().isEmpty() ||
                numberField.getText().isEmpty() ||
                postalcodeField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                countryField.getText().isEmpty();
    }

    private boolean isTypeNotChosen(){
        if(locationTypeComboBox.getValue() == null){
            return true;
        }
        return false;
    }

    private boolean isOwnerNotChosen(){
        return ownerButton.getText().equals("Pick owner");
    }
    private boolean validHouseNumber(){
        try {
            Integer.parseInt(numberField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller){
        this.listener = controller;
    }
}
