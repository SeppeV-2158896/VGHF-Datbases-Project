package be.vghf.controllers;

import be.vghf.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccountController implements Controller {
    public User getLoggedInUser;
    @FXML private Button registerButton;
    @FXML private Button loginButton;

    private BaseController baseController;

    public AccountController() {

    }

    public void handleRegisterButton(ActionEvent actionEvent) {
        registerButton.getScene().getWindow().hide();
        baseController.showView("Register", new RegisterController(), "/registerForm-view.fxml");
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        loginButton.getScene().getWindow().hide();
        baseController.showView("Login", new LoginController(), "/loginForm-view.fxml");
    }
    @Override
    public void setBaseController (BaseController baseController){
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller){
        //no listener needed here
    }
}
