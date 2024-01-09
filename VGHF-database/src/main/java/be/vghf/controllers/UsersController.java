package be.vghf.controllers;

import be.vghf.domain.Dev_company;
import be.vghf.domain.User;
import be.vghf.repository.Dev_companyRepository;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class UsersController implements Controller {
    BaseController baseController;
    @FXML private TextField nameQueryField;
    @FXML private TextField locationQueryField;
    @FXML private Text nameText;
    @FXML private Text locationText;
    @FXML private Button submitButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    @FXML private Button loansButton;
    @FXML private TableView<User> userTableView;

    @FXML
    public void initialize(){


        TableColumn<User, String> usernameCol = new TableColumn<User, String>("Username");
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("userName"));

        TableColumn<User, String> firstNameCol = new TableColumn<User, String>("First name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<User,String>("Last Name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName"));

        TableColumn<User, String> addressCol = new TableColumn<User, String>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("address"));

        TableColumn<User, String> telCol = new TableColumn<User, String>("Telephone");
        telCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("telephone"));

        TableColumn<User, String> emailCol = new TableColumn<User, String>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("email"));

        TableColumn<User, String> typeCol = new TableColumn<User,String>("Type");
        typeCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("userType"));

        TableColumn<User, Double> totalFineCol = new TableColumn<User,Double>("Total Payed Fines");
        totalFineCol.setCellValueFactory(
                new PropertyValueFactory<User, Double>("totalFine"));
        TableColumn<User, Double> outStandingFineCol = new TableColumn<User,Double>("Outstanding Fine");
        outStandingFineCol.setCellValueFactory(
                new PropertyValueFactory<User, Double>("outStandingFine"));
        TableColumn<User, Integer> activeLoansCol = new TableColumn<User, Integer>("Amount of items in loan");
        activeLoansCol.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("amountOfActiveLoans"));

        userTableView.setItems(FXCollections.observableList(new UserRepository().getAll()));
        userTableView.setEditable(false);
        userTableView.getColumns().addAll(usernameCol, firstNameCol, lastNameCol, addressCol, telCol, emailCol, typeCol, totalFineCol, outStandingFineCol, activeLoansCol);

        submitButton.setText("Search");
        submitButton.setOnAction(event -> handleSearch(event));

        editButton.setText("Edit");
        editButton.setOnAction(event -> handleEdit(event));
        editButton.setDisable(true);

        addButton.setText("Add");
        addButton.setOnAction(event -> handleAdd(event));
        addButton.setVisible(false);

        loansButton.setText("Loaned Items");
        loansButton.setOnAction(event -> handleLoans(event));
        loansButton.setDisable(true);
    }



    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {

    }

    @FXML protected void handleSearch(ActionEvent event) {
        String nameText = nameQueryField.getText();
        String[] nameArray = nameText.split("\\s+");
        String locationText = locationQueryField.getText();
        String[] addressArray = locationText.split("\\s+");

        UserRepository userRepository = new UserRepository();
        List<User> results = new ArrayList<>();
        if(!nameText.isEmpty() && locationText.isEmpty()){
            for (String str : nameArray) {
                results.addAll(userRepository.getUserByName(str));
            }
        }
        else if (nameText.isEmpty() && !locationText.isEmpty()){
            results.addAll(userRepository.getUserByAddress(addressArray));
        }
        else if (!nameText.isEmpty() && !locationText.isEmpty()){
            Set<User> nameResults = new HashSet<>();
            for (String str : nameArray) {
                nameResults.addAll(userRepository.getUserByName(str));
            }
            List<User> addressResults = userRepository.getUserByAddress(addressArray);
            results = nameResults.stream()
                    .filter(addressResults::contains)
                    .collect(Collectors.toList());
        }

        else {
            results = userRepository.getAll();
        }

        userTableView.setItems(FXCollections.observableArrayList(results));
    }

    @FXML protected void handleEdit(ActionEvent event){
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        EditUserController editUserController = new EditUserController();
        editUserController.setUser(selectedUser);
        editUserController.setListener(this);
        baseController.showView("Edit user", editUserController, "/createUser-view.fxml");
    }

    @FXML protected void handleAdd(Event event) {
        CreateUserController createUserController = new CreateUserController();
        createUserController.setListener(this);
        baseController.showView("New user", createUserController, "/createUser-view.fxml");
    }

    @FXML protected void handleDelete(Event event){
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        GenericRepository.delete(selectedUser);

        var items = userTableView.getItems();
        items.remove(selectedUser);
        userTableView.setItems(items);

        editButton.setDisable(true);
    }

    private void handleLoans(ActionEvent event) {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        var loanedItemsController = new UsersLoanedItemsController();
        baseController.showView("Edit user", loanedItemsController, "/userLoanedItems-view.fxml");
        loanedItemsController.setUser(selectedUser);
        loanedItemsController.setListener(this);
    }
    public void newUserCreated(User user){
        GenericRepository.save(user);

        var items = userTableView.getItems();
        items.add(user);
        userTableView.setItems(items);
    }

    public void userEdited(User user){
        GenericRepository.update(user);
        userTableView.refresh();
    }
}
