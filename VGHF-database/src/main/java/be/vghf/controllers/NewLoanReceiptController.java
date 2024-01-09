package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.Loan_Receipts;
import be.vghf.domain.Location;
import be.vghf.repository.GameRepository;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.Loan_ReceiptsRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class NewLoanReceiptController implements Controller {
    @FXML private TextField loanTermField;

    private Loan_ReceiptsRepository loanReceiptRepository = new Loan_ReceiptsRepository();
    private GameRepository gameRepository = new GameRepository();
    private Game game;
    private BaseController baseController;

    @FXML
    public void initialize() {

    }

    public void setGame(Game game){
        this.game = game;
    }

    @FXML
    public void createLoanReceipt() {
        int loanTerm = Integer.parseInt(loanTermField.getText());

        // Create a new loan receipt
        Loan_Receipts newLoanReceipt = new Loan_Receipts();
        newLoanReceipt.setGame(game);
        newLoanReceipt.setLoanTerm(loanTerm);
        newLoanReceipt.setLoanedDate(LocalDate.now().toString());


        GenericRepository.save(newLoanReceipt);

        this.loanTermField.getScene().getWindow().hide();

    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {

    }
}
