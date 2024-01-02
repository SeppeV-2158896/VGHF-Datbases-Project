package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import be.vghf.repository.Repository;
import be.vghf.repository.UserRepository;
import com.sun.javafx.scene.control.IntegerField;
import com.sun.jdi.IntegerValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController {
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

    public void registerUser(ActionEvent actionEvent) {
        if (areFieldsEmpty()){
            showErrorAlert("Error", "All fields with a * should be filled in!");
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
            showErrorAlert("Error", "Passwords don't match");
        }

        user.setUserType(UserType.CLIENT);

        Repository.save(user);
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

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
