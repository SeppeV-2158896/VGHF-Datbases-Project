package be.vghf.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccountController {
    @FXML
    private Button registerButton;
    @FXML private Button loginButton;
    public AccountController() {

    }

    public void handleRegisterButton(ActionEvent actionEvent) {
        registerButton.getScene().getWindow().hide();
        BaseController.showView("/registerForm-view.fxml");
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        loginButton.getScene().getWindow().hide();
    }

}
