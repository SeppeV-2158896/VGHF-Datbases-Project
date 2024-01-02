package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.models.ActiveUser;
import be.vghf.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.util.List;

public class LoginController implements Controller{

    @FXML private TextField user;
    @FXML private TextField password;
    private BaseController baseController;


    public void handleLoginButton(ActionEvent actionEvent) {
        String[] info = user.getText().split("[.]",2);
        List<User> users = UserRepository.getUserByName(info);
        if (users.isEmpty()){
            BaseController.showErrorAlert("ALERT", "This name is not yet in use");
            return;
        }
        User user = users.get(0);
        String hashedPassword = user.getPassword();
        if (UserRepository.hashPassword(password.getText()).equals(hashedPassword)){
            this.user.getScene().getWindow().hide();
            ActiveUser.user = user;
            baseController.update();
        }
        else {
            BaseController.showErrorAlert("ALERT", "You provided the incorrect password!");
        }
    }

    public void setBaseController (BaseController baseController){
        this.baseController = baseController;
    }
}