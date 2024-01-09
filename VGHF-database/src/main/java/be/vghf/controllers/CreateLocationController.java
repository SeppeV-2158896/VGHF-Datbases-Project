package be.vghf.controllers;

import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.enums.LocationType;
import be.vghf.enums.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateLocationController implements Controller {
    @FXML
    private Button ownerButton;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField houseNumberField;
    @FXML
    private TextField busField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private ComboBox<LocationType> typeComboBox;
    private BaseController baseController;
    private Controller listener;
    private User owner = null;

    @FXML protected void initialize(){
        typeComboBox.setPromptText("Location type");
        typeComboBox.getItems().add(LocationType.PRIVATE);
        typeComboBox.getItems().add(LocationType.STORAGE);
        typeComboBox.getItems().add(LocationType.EXPO);
        typeComboBox.getItems().add(LocationType.MUSEUM);
        typeComboBox.getItems().add(LocationType.LIBRARY);
    }

    @FXML protected void saveLocation(ActionEvent event){
        Location location = new Location();
        //setters + check als data = geldig

        if(validInformation()){
            location.setOwner(owner);
            location.setStreetName(streetNameField.getText());
            location.setHouseNumber(Integer.parseInt(houseNumberField.getText()));
            location.setBus(busField.getText());
            location.setPostalCode(postalCodeField.getText());
            location.setCity(cityField.getText());
            location.setCountry(countryField.getText());
            location.setLocationType(typeComboBox.getValue());

            if(listener instanceof EditGameLocationController){
                ((EditGameLocationController) listener).newLocationCreated(location);
            }
            else{
            }

            Button sourceButton = (Button) event.getSource();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.close();
        }
        else{
            displayErrorAlert("ERROR", "Invalid input\n" +
                    "Check if all fields are properly filled in.\n" +
                    "(House number and telephone have to be numbers only!)");
        }
    }

    @FXML protected void handleChangeOwner(ActionEvent event){
        EditGameOwnerController egoController = new EditGameOwnerController();
        egoController.setListener(this);
        baseController.showView("Edit owner", egoController, "/editGameOwner-view.fxml");
    }

    public void selectedOwnerConfirmed(User owner){
        this.owner = owner;
        ownerButton.setText(owner.getFirstName() + " " + owner.getLastName());
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {
        this.listener = controller;
    }

    private boolean validInformation(){
        try{
            Integer.parseInt(houseNumberField.getText());
        } catch(NumberFormatException e){
            return false;
        }

        if(     owner == null ||
                streetNameField.getText().isEmpty() ||
                houseNumberField.getText().isEmpty() ||
                busField.getText().isEmpty() ||
                postalCodeField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                countryField.getText().isEmpty() ||
                typeComboBox.getValue() == null){
            return false;
        }
        else{
            return true;
        }
    }

    private void displayErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
