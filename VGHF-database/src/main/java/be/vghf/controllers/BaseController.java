package be.vghf.controllers;

import be.vghf.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    @FXML private Text testTekst;
    @FXML private Button accountButton;
    @FXML private Button usersButton;
    @FXML private Button eventsButton;
    @FXML private Button browseButton;
    @FXML private Button loanedItemsButton;
    private BaseController.loginStatus currentStatus;

    private enum loginStatus {LOGGED_IN, LOGGED_OUT, ADMIN}

    @FXML
    public void initialize() {
        usersButton.setVisible(false);
        loanedItemsButton.setVisible(false);

        this.currentStatus = loginStatus.LOGGED_OUT;
    }

    @FXML protected void handleAccountButtonPressed(ActionEvent event) throws IOException {
        testTekst.setText("Account knop geduwd");
        System.out.println("knop geduwd");

        var repo = new UserRepository();
        for (var user : repo.getAllUsers()){
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }

        showView("/loginOrRegister-view.fxml");
    }

    public static Stage showView(String path){
        try {
            Parent root = FXMLLoader.load(BaseController.class.getResource(path));

            Stage stage = new Stage();
            stage.setTitle("VGHF Database Software");
            stage.setScene(new Scene(root));
            stage.show();

            return stage;

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
