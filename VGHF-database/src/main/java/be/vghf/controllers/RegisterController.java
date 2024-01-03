package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegisterController implements Controller{
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField streetNameField;
    @FXML private TextField houseNumberField;
    @FXML private TextField busField;
    @FXML private TextField postalCodeField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;
    private BaseController baseController;

    public void registerUser(ActionEvent actionEvent) {
        if (areFieldsEmpty()){
            BaseController.showErrorAlert("Error", "All fields with a * should be filled in!");
        }

        if(!emailField.getText().contains("@")){
            BaseController.showErrorAlert("Error", "Email must contain '@'.");
        }

        var user = new User();
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setStreetName(streetNameField.getText());
        user.setHouseNumber(Integer.valueOf(houseNumberField.getText()));
        user.setPostalCode(postalCodeField.getText());
        user.setCity(lastNameField.getText());
        user.setCountry(countryField.getText());
        user.setEmail(emailField.getText());

        if (busField.getText().isEmpty()) user.setBus(null);
        else user.setBus(busField.getText());
        if (telephoneField.getText().isEmpty()) user.setTelephone(0);
        else user.setTelephone(Integer.valueOf(telephoneField.getText()));

        if (passwordField.getText().equals(confirmPasswordField.getText())){
            user.setPassword(UserRepository.hashPassword(passwordField.getText()));
        }
        else {
            BaseController.showErrorAlert("Error", "Passwords don't match");
        }


        user.setUserType(UserType.CLIENT);

        GenericRepository.save(user);
    }

    private boolean areFieldsEmpty() {
        return firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                streetNameField.getText().isEmpty() ||
                houseNumberField.getText().isEmpty() ||
                postalCodeField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                countryField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                confirmPasswordField.getText().isEmpty();
    }

    public void setBaseController (BaseController baseController){
        this.baseController = baseController;
    }


}
