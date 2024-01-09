package be.vghf.controllers;

import be.vghf.domain.Loan_Receipts;
import be.vghf.domain.User;
import be.vghf.models.ActiveUser;
import be.vghf.repository.Loan_ReceiptsRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.List;

public class LoanedItemsController implements Controller {
    @FXML
    private TableView<Loan_Receipts> loansTableView;
    @FXML private AnchorPane anchorPane;
    private User user;
    private BaseController baseController;
    private Controller listener;

    @FXML
    public void initialize(){
        anchorPane.setMinWidth(1900);
        anchorPane.setPrefWidth(1900);

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

        loansTableView.setRowFactory(tv -> {
            TableRow<Loan_Receipts> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldItem, loan) -> {
                if (loan == null) {
                    row.setStyle("");
                } else {
                    if (loan.getFine() != null) {
                        row.setStyle("-fx-background-color: #F093CE");
                    } else if(loan.getReturnDate() == null) {
                        row.setStyle("-fx-background-color: #FFC485");
                    } else{
                        row.setStyle("-fx-background-color: #A7B0F1");
                    }
                }
            });
            return row;
        });

        loansTableView.getColumns().addAll(gameNameCol, loanerNameCol, loanedDateCol, loanTermCol, returnDateCol, fineCol);
        loansTableView.prefWidthProperty().bind(anchorPane.widthProperty());
    }

    @Override
    public void setBaseController(BaseController baseController) {this.baseController = baseController;}

    @Override
    public void setListener(Controller controller) {this.listener = controller;}

    public void setUser(User user) {
        this.user = user;
        loadLoans();
    }

    private void loadLoans() {
        if (user != null) {
            List userLoans = Loan_ReceiptsRepository.getLoansByUser(user);
            if (!userLoans.isEmpty()) {
                loansTableView.setItems(FXCollections.observableList(userLoans));
            }
        }
    }
}
