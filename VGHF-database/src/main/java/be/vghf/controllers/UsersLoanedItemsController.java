package be.vghf.controllers;

import be.vghf.domain.Loan_Receipts;
import be.vghf.domain.User;
import be.vghf.repository.Loan_ReceiptsRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UsersLoanedItemsController implements Controller {
    @FXML private TableView<Loan_Receipts> loansTableView;
    private User user;
    private BaseController baseController;
    private Controller listener;

    @FXML public void initialize(){
        TableColumn<Loan_Receipts, String> gameNameCol = new TableColumn<>("Game Name");
        gameNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGame().getTitle()));

        TableColumn<Loan_Receipts, String> loanerNameCol = new TableColumn<>("Loaner Name");
        loanerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getFirstName() + " " + cellData.getValue().getCustomer().getLastName()));

        TableColumn<Loan_Receipts, String> loanedDateCol = new TableColumn<>("Loaned Date");
        loanedDateCol.setCellValueFactory(new PropertyValueFactory<>("loanedDate"));

        TableColumn<Loan_Receipts, Integer> loanTermCol = new TableColumn<>("Loan Term (Days)");
        loanTermCol.setCellValueFactory(new PropertyValueFactory<>("loanTerm"));

        TableColumn<Loan_Receipts, String> returnDateCol = new TableColumn<>("Return Date");
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        TableColumn<Loan_Receipts, Double> fineCol = new TableColumn<>("Fine");
        fineCol.setCellValueFactory(new PropertyValueFactory<>("fine"));

        loansTableView.getColumns().addAll(gameNameCol, loanerNameCol, loanedDateCol, loanTermCol, returnDateCol, fineCol);

    }

    public void setUser(User user) {
        this.user = user;
        loadLoans();
    }

    private void loadLoans() {
        if (user != null) {
            // Assuming LoanReceiptRepository has a method to get loans by user
            List userLoans = Loan_ReceiptsRepository.getLoansByUser(user);
            if (!userLoans.isEmpty()) {
                loansTableView.setItems(FXCollections.observableList(userLoans));
            }
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
