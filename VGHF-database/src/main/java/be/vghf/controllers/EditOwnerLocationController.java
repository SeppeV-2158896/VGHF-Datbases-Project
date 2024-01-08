package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.User;
import be.vghf.repository.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EditOwnerLocationController implements Controller{
    private BaseController baseController;
    @FXML
    private TextField ownerField;
    @FXML
    private TextField addressField;
    @FXML
    private TableView table;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button confirmButton;

    private GameAdminController listener;

    @FXML public void initialize(){
        EditOwnerLocationController controller = new EditOwnerLocationController();

        TableColumn<User, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> {
            String address = cellData.getValue().getAddress();
            return new SimpleStringProperty(address != null ? address : "");
        });

        table.getColumns().addAll(firstNameColumn, lastNameColumn, addressColumn);

        var results = new UserRepository().getAll();
        table.setItems(FXCollections.observableArrayList(results));

        confirmButton.setDisable(true);
    }

    @FXML protected void handleSearch(KeyEvent event) throws IOException {
        if(event.getCode() != KeyCode.ENTER){
            return;
        }

        String ownerText = ownerField.getText();
        String addressText = addressField.getText();
        String[] addressArray = addressText.split("\\s+");

        UserRepository userRepository = new UserRepository();
        List<User> results = new ArrayList<>();
        if(!ownerText.isEmpty() && addressText.isEmpty()){
            results.addAll(userRepository.getUserByName(ownerText));
        }
        else if (ownerText.isEmpty() && !addressText.isEmpty()){
            results.addAll(userRepository.getUserByAddress(addressArray));
        }
        else if (!ownerText.isEmpty() && !addressText.isEmpty()){
            List<User> nameResults = userRepository.getUserByName(ownerText);
            List<User> addressResults = userRepository.getUserByAddress(addressArray);
            results = nameResults.stream()
                    .filter(addressResults::contains)
                    .collect(Collectors.toList());
        }

        table.setItems(FXCollections.observableArrayList(results));

        //System.out.println("searchText: " + searchText);
    }

    @FXML protected void handleMouseClick(MouseEvent event){
        if(event.getClickCount() == 1 && table.getSelectionModel().getSelectedItem() != null){
            confirmButton.setDisable(false);
        }
        else{
            confirmButton.setDisable(true);
        }
    }

    @FXML protected void confirmSelectedOwner(ActionEvent event){
        User selectedOwner = (User) table.getSelectionModel().getSelectedItem();
        if (selectedOwner != null && listener != null) {
            listener.selectedUserConfirmed(selectedOwner);

            Button sourceButton = (Button) event.getSource();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML protected void addOwner(ActionEvent event){
        return;
    }

    @FXML protected void deleteOwner(ActionEvent event){
        return;
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller){
        this.listener = (GameAdminController) controller;
    }
}