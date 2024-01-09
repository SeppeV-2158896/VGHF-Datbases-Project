package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserController implements Controller{
    @FXML
    private Label title;
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
    private User user;
    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {
        this.listener = controller;
    }

    public void setUser(User user){
        this.user = user;
    }

    @FXML public void initialize(){
        title.setText("Edit user");

        usernameField.setText(user.getUserName());
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        streetNameField.setText(user.getStreetName());
        houseNumberField.setText(Integer.toString(user.getHouseNumber()));
        busField.setText(user.getBus());
        postalCodeField.setText(user.getPostalCode());
        cityField.setText(user.getCity());
        countryField.setText(user.getCountry());
        telephoneField.setText(Integer.toString(user.getTelephone()));
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
    }

    @FXML protected void saveUser(ActionEvent event){
        if(validInformation()){
            user.setUserName(usernameField.getText());
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setStreetName(streetNameField.getText());
            user.setHouseNumber(Integer.parseInt(houseNumberField.getText()));
            user.setBus(busField.getText());
            user.setPostalCode(postalCodeField.getText());
            user.setCity(cityField.getText());
            user.setCountry(countryField.getText());
            user.setTelephone(Integer.parseInt(telephoneField.getText()));
            user.setEmail(emailField.getText());
            user.setPassword(passwordField.getText());

            if (listener.getClass() == UsersController.class) {
                ((UsersController) listener).userEdited(user);
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
