package be.vghf.controllers;

import be.vghf.domain.Console;
import be.vghf.domain.Dev_company;
import be.vghf.domain.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateCompanyController implements Controller{
    @FXML
    private Label title;
    @FXML
    private TextField companyNameField;
    @FXML
    private TextField websiteField;
    @FXML
    private TextField supportEmailField;
    @FXML
    private TextField establishedDateField;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField houseNumberField;
    @FXML
    private TextField busField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private Button saveButton;
    private BaseController baseController;
    private Controller listener;
    private Dev_company company;
    private boolean newCompany;

    @FXML protected void initialize(){
        if(company != null){
            newCompany = false;
            populateFields();
        }
        else{
            company = new Dev_company();
            newCompany = true;
        }
    }

    public void setCompany(Dev_company company){
        this.company = company;
    }

    @FXML protected void saveCompany(ActionEvent event){
        if(validInformation()){
            company.setCompanyName(companyNameField.getText());
            company.setWebsite(websiteField.getText());
            company.setSupportEmail(supportEmailField.getText());
            company.setEstablishedDate(establishedDateField.getText());
            company.setStreetName(streetNameField.getText());
            company.setHouseNumber(Integer.parseInt(houseNumberField.getText()));
            company.setBus(busField.getText());
            company.setPostalCode(postalCodeField.getText());
            company.setCity(cityField.getText());
            company.setCountry(countryField.getText());

            if(!newCompany){
                ((BrowseController) listener).companyEdited(company);
            }
            else if(listener instanceof SelectCompanyController){
                ((SelectCompanyController) listener).newCompanyCreated(company);
            }
            else if(listener instanceof BrowseController){
                ((BrowseController) listener).newCompanyCreated(company);
            }

            Button sourceButton = (Button) event.getSource();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.close();
        }
        else{
            displayErrorAlert("ERROR", "Invalid input\n" +
                    "Check if all fields are properly filled in.\n" +
                    "(House number and telephone have to be numbers only!)");
        }
    }

    private boolean validInformation(){
        try{
            Integer.parseInt(houseNumberField.getText());
        } catch(NumberFormatException e){
            return false;
        }

        if(     companyNameField.getText().isEmpty() ||
                websiteField.getText().isEmpty() ||
                supportEmailField.getText().isEmpty() || !supportEmailField.getText().contains("@") ||
                establishedDateField.getText().isEmpty() ||
                streetNameField.getText().isEmpty() ||
                busField.getText().isEmpty() ||
                postalCodeField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                countryField.getText().isEmpty()){
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

    private void populateFields(){
        companyNameField.setText(company.getCompanyName());
        websiteField.setText(company.getWebsite());
        supportEmailField.setText(company.getSupportEmail());
        establishedDateField.setText(company.getEstablishedDate());
        streetNameField.setText(company.getStreetName());
        houseNumberField.setText(Integer.toString(company.getHouseNumber()));
        busField.setText(company.getBus());
        postalCodeField.setText(company.getPostalCode());
        cityField.setText(company.getCity());
        countryField.setText(company.getCountry());
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
