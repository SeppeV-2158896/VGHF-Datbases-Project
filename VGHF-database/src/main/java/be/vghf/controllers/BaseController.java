package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.repository.Repository;
import be.vghf.repository.UserRepositoryImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class BaseController {

    @FXML private Text testTekst;


    @FXML protected void handleAccountButtonPressed(ActionEvent event){
        testTekst.setText("Account knop geduwd");
        System.out.println("knop geduwd");

        new Repository();
    }
}
