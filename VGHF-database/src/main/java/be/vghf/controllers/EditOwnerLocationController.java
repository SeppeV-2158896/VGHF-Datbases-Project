package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.User;
import be.vghf.repository.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    @FXML protected void addOwner(){

    }

    @FXML protected void deleteOwner(){

    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }
}
