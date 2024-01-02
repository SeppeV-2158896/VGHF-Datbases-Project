package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import be.vghf.repository.Repository;
import be.vghf.repository.UserRepositoryImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.FileInputStream;

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

    @FXML protected void handleAccountButtonPressed(ActionEvent event){
        testTekst.setText("Account knop geduwd");
        System.out.println("knop geduwd");

        var repo = new UserRepositoryImpl();
        for (var user : repo.getAllUsers()){
            System.out.println(user.getFirstName() + " " + user.getLastName());
        }
    }
}
