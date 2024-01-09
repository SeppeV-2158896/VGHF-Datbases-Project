package be.vghf.controllers;

import be.vghf.domain.Console;
import be.vghf.domain.Dev_company;
import be.vghf.enums.ConsoleType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class CreateConsoleController implements Controller{
    @FXML private TextField consoleNameField;
    @FXML private ComboBox<ConsoleType> typeComboBox;
    @FXML private Button companyButton;
    @FXML private TextField releaseYearField;
    @FXML private TextField discontinuedYearField;
    @FXML private TextField unitsSoldField;
    @FXML private TextField remarksField;
    @FXML private Button saveButton;
    private Console console;
    private BaseController baseController;
    private Controller listener;

    @FXML protected void initialize(){
        typeComboBox.setPromptText("Select console type");
        typeComboBox.getItems().add(ConsoleType.STANDALONE);
        typeComboBox.getItems().add(ConsoleType.HANDHELD);
        typeComboBox.getItems().add(ConsoleType.HYBRID);

        if(console != null){
            populateFields();
        }
        else{
            console = new Console();
        }
    }

    @FXML protected void handleChangeCompany(ActionEvent event){
        SelectCompanyController selectCompanyController = new SelectCompanyController();
        selectCompanyController.setListener(this);
        baseController.showView("Select company", selectCompanyController, "/selectCompany-view.fxml");
    }

    public void selectedCompanyConfirmed(Dev_company newCompany){
        console.setCompany(newCompany);
        companyButton.setText(newCompany.getCompanyName());
    }

    @FXML protected void saveConsole(ActionEvent event){
        if(!validInformation()){
            displayErrorAlert("ERROR", "Invalid input\n" +
                    "Check if all fields are properly filled in.\n" +
                    "(Dates should be yyyy-mm-dd)");
            return;
        }

        console.setConsoleName(consoleNameField.getText());
        console.setConsoleType(typeComboBox.getValue());
        console.setReleaseYear(releaseYearField.getText());
        console.setDiscontinuationYear(discontinuedYearField.getText().isEmpty() ? null : discontinuedYearField.getText());
        double unitsSold = 0;
        if(!unitsSoldField.getText().isEmpty()){
            unitsSold = Double.parseDouble(unitsSoldField.getText());
        }
        console.setUnitsSoldInMillions(unitsSold);
        console.setRemarks(remarksField.getText().isEmpty() ? null : remarksField.getText());

        if(listener instanceof BrowseController){
            ((BrowseController) listener).handleConsoleCreated(console);
        }

        Button sourceButton = (Button) event.getSource();
        Stage stage = (Stage) sourceButton.getScene().getWindow();
        stage.close();
    }

    private boolean validInformation(){
        try{
            if(!unitsSoldField.getText().isEmpty()) {
                Double.parseDouble(unitsSoldField.getText());
            }

            LocalDate.parse(releaseYearField.getText());

            if(!discontinuedYearField.getText().isEmpty()){
                LocalDate.parse(discontinuedYearField.getText());
            }
        } catch (Exception e){
            return false;
        }

        if( consoleNameField.getText().isEmpty() ||
            typeComboBox.getValue() == null ||
            console.getCompany() == null){
            return false;
        }
        else{
            return true;
        }
    }

    private void displayErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setConsole(Console console){
        this.console = console;
    }

    private void populateFields(){
        consoleNameField.setText(console.getConsoleName());
        typeComboBox.setValue(console.getConsoleType());
        companyButton.setText(console.getCompany().getCompanyName());
        releaseYearField.setText(console.getReleaseYear());
        discontinuedYearField.setText(console.getDiscontinuationYear());
        unitsSoldField.setText(Double.toString(console.getUnitsSoldInMillions()));
        remarksField.setText(console.getRemarks());
    }
    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {
        this.listener = controller;
    }
}
