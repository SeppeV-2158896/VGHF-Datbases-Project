package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateUserController implements Controller{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
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
    private TextField telephoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    private BaseController baseController;
    private Controller listener;
    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {
        this.listener = controller;
    }

    @FXML public void initialize(){
        //nothing for now
    }

    @FXML protected void saveNewOwner(ActionEvent event){
        User owner = new User();
        //setters + check als data = geldig

        if(validInformation()){
            owner.setUserName(usernameField.getText());
            owner.setFirstName(firstNameField.getText());
            owner.setLastName(lastNameField.getText());
            owner.setStreetName(streetNameField.getText());
            owner.setHouseNumber(Integer.parseInt(houseNumberField.getText()));
            owner.setBus(busField.getText());
            owner.setPostalCode(postalCodeField.getText());
            owner.setCity(cityField.getText());
            owner.setCountry(countryField.getText());
            owner.setTelephone(Integer.parseInt(telephoneField.getText()));
            owner.setEmail(emailField.getText());
            owner.setPassword(passwordField.getText());

            owner.setUserType(UserType.VOLUNTEER);

            if(listener instanceof EditOwnerLocationController){
                ((EditOwnerLocationController) listener).newOwnerCreated(owner);
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

    private boolean validInformation(){
        try{
            Integer.parseInt(houseNumberField.getText());
            Integer.parseInt(telephoneField.getText());
        } catch(NumberFormatException e){
            return false;
        }

        if(     usernameField.getText().isEmpty() ||
                firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                streetNameField.getText().isEmpty() ||
                houseNumberField.getText().isEmpty() ||
                busField.getText().isEmpty() ||
                postalCodeField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                countryField.getText().isEmpty() ||
                telephoneField.getText().isEmpty() ||
                emailField.getText().isEmpty() || !emailField.getText().contains("@") ||
                passwordField.getText().isEmpty()){
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
