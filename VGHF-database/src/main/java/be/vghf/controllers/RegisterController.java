package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.Repository;
import be.vghf.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class RegisterController implements Controller{
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
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
            return;

        }
        var user = new User();
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setStreetName(streetNameField.getText());

        if(validHouseNumber()){
            user.setHouseNumber(Integer.valueOf(houseNumberField.getText()));
        }else{
            BaseController.showErrorAlert("Error", "The house number must be a number");
            return;

        }

        user.setPostalCode(postalCodeField.getText());
        user.setCity(lastNameField.getText());
        user.setCountry(countryField.getText());

        if(validEmail()){
            user.setEmail(emailField.getText());
        }else{
            BaseController.showErrorAlert("Error", "The e-mailadres is invalid");
            return;
        }

        if (busField.getText().isEmpty()) user.setBus(null);
        else user.setBus(busField.getText());

        if (telephoneField.getText().isEmpty()){
            user.setTelephone(0);
        } else if(validTelephoneNumber()){
            user.setTelephone(Integer.valueOf(telephoneField.getText()));
        }else{
            BaseController.showErrorAlert("Error", "The telephone number can only contain numbers");
            return;

        }


        if (passwordField.getText().equals(confirmPasswordField.getText())){
            user.setPassword(UserRepository.hashPassword(passwordField.getText()));
        }
        else {
            BaseController.showErrorAlert("Error", "Passwords don't match");
            return;

        }

        if (isUsernameUnique(usernameField.getText())){
            user.setUserName(usernameField.getText());
        }
        else {
            BaseController.showErrorAlert("Error", "Username already in use");
            return;

        }

        user.setUserType(UserType.CUSTOMER);

        GenericRepository.save(user);

        ActiveUser.user = user;
        baseController.update();
        firstNameField.getScene().getWindow().hide();
    }

    private boolean validEmail(){
        if((emailField.getText()).contains("@")){
            return true;
        }
        return false;
    }

    private boolean isUsernameUnique(String username){
        return UserRepository.getUserByName(username).isEmpty();
    }

    private boolean validHouseNumber(){
        try {
            Integer.parseInt(houseNumberField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validTelephoneNumber(){
        try {
            Integer.parseInt(telephoneField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean areFieldsEmpty() {
        return firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                usernameField.getText().isEmpty() ||
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
