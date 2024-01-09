package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.models.ActiveUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LogoutController implements Controller {
    public User getLoggedInUser;
    @FXML private Label logoutText;
    @FXML private Button logoutButton;

    private BaseController baseController;

    @FXML public void initialize(){
        var name = ActiveUser.user.getFirstName();
        logoutText.setText("Welcome " + name);
    }

    public LogoutController() {}


    public void handleLogoutButton(ActionEvent actionEvent){
        this.logoutText.getScene().getWindow().hide();
        ActiveUser.user = null;
        baseController.update();
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
