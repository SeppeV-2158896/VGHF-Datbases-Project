package be.vghf.controllers;

import be.vghf.domain.Dev_company;
import be.vghf.domain.User;
import be.vghf.repository.Dev_companyRepository;
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

import java.util.ArrayList;
import java.util.List;

public class SelectCompanyController implements Controller{
    @FXML private TextField companyField;
    @FXML private TableView<Dev_company> table;
    @FXML private Button addButton;
    @FXML private Button confirmButton;
    private BaseController baseController;
    private Controller listener;

    @FXML protected void initialize(){
        TableColumn<Dev_company, String> nameCol = new TableColumn<Dev_company, String>("Company name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Dev_company, String>("companyName"));

        TableColumn<Dev_company, String> websiteCol = new TableColumn<Dev_company, String>("Website");
        websiteCol.setCellValueFactory(
                new PropertyValueFactory<Dev_company, String>("website"));

        TableColumn<Dev_company, String> emailCol = new TableColumn<Dev_company,String>("Email");
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Dev_company, String>("supportEmail"));

        TableColumn<Dev_company, String> addressCol = new TableColumn<Dev_company, String>("Address");
        addressCol.setCellValueFactory(
                new PropertyValueFactory<Dev_company, String>("address"));

        table.setEditable(false);
        table.getColumns().addAll(nameCol, websiteCol, emailCol, addressCol);
        table.setItems(FXCollections.observableList(new Dev_companyRepository().getAll()));

        confirmButton.setDisable(true);
    }

    @FXML protected void handleSearch(KeyEvent event){
        if(event.getCode() != KeyCode.ENTER){
            return;
        }
        String companyText = companyField.getText();
        String[] companyArray = companyText.split("\\s+");

        Dev_companyRepository devCompanyRepository = new Dev_companyRepository();
        List<Dev_company> results = new ArrayList<>();
        if(companyText.isEmpty()){
            devCompanyRepository.getAll();
        }
        else{
            devCompanyRepository.getCompaniesByName(companyArray);
        }
        table.setItems(FXCollections.observableArrayList(results));
    }

    @FXML protected void handleMouseClick(MouseEvent event){
        if(event.getClickCount() == 1 && table.getSelectionModel().getSelectedItem() != null){
            confirmButton.setDisable(false);
        }
        else{
            confirmButton.setDisable(true);
        }
    }

    @FXML protected void addCompany(ActionEvent event){

    }

    @FXML protected void confirmSelectedCompany(ActionEvent event){
        Dev_company selectedCompany = table.getSelectionModel().getSelectedItem();
        if (selectedCompany != null && listener != null) {
            if(listener instanceof CreateConsoleController){
                ((CreateConsoleController) listener).selectedCompanyConfirmed(selectedCompany);
            }

            Button sourceButton = (Button) event.getSource();
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.close();
        }
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
