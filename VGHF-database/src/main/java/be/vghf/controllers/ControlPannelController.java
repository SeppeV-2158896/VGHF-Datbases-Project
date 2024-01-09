package be.vghf.controllers;

import be.vghf.domain.Console;
import be.vghf.domain.Date;
import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.ConsoleRepository;
import be.vghf.repository.GameRepository;
import be.vghf.repository.GenericRepository;
import be.vghf.repository.Loan_ReceiptsRepository;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ControlPannelController implements Controller{

    @FXML private TextField gameSearchText;
    @FXML private TilePane gamesTableView;

    private Location location;
    private BaseController baseController;
    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public void setListener(Controller controller) {

    }

    @FXML public void initialize(){
        var gameRepository = new GameRepository();
        var games = gameRepository.getAll();

        showGamesInTileView(new HashSet<>(games));

        gameSearchText.setOnKeyReleased(this::handleGameSearch);
    }

    public void setLocation(Location location){
        this.location = location;
        showGamesInTileView(new GameRepository().getAllByLocation(location));

    }

    private void handleGameSearch(KeyEvent keyEvent) {
        if(keyEvent.getCode() != KeyCode.ENTER){
            return;
        }

        String gameSearchText = this.gameSearchText.getText();
        String[] gameSearch = gameSearchText.split("\\s+");
        Set<Game> gameResults = null;

        showGamesInTileView(gameResults);
    }

    private void showGamesInTileView(Set<Game> games) {

        try {
            gamesTableView.getChildren().clear();
            gamesTableView.setVgap(10);
            gamesTableView.setHgap(10);
            gamesTableView.setPrefColumns( gamesTableView.getPrefWidth() == 0 ? 4 : (int) gamesTableView.getPrefWidth()/200);

            // Populating TilePane with AnchorPanes
            for (var game : games) {
                gamesTableView.getChildren().add(createTileView(game)); // Add AnchorPane to TilePane
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private AnchorPane createTileView(Game game){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(200, 100);
        anchorPane.setStyle("-fx-background-color: #81fa84");

        VBox layout = new VBox();

        Label label1 = new Label("Game: " + game.getTitle());
        Label label2 = new Label("Release date: " + game.getReleaseDate().toString());
        Label label3 = new Label("Consoles: " + GenericRepository.consoleSetToString(game.getConsoles()));
        Label label4 = new Label("Location: " + game.getCurrentLocation().toString());
        Label label5 = new Label("Genre: " + game.getGenre());

        var activeLoan = Loan_ReceiptsRepository.getActiveLoanByGame(game);
        String expectedReturn = "";

        if (activeLoan != null) {
            expectedReturn = new Date (activeLoan.getLoanedDate()).getDate().plusDays(activeLoan.getLoanTerm()).toString();
        }
        Label label6 = new Label("Loaned until: " + (activeLoan == null ? "RETURNED" :  expectedReturn));

        layout.getChildren().addAll(label1,label2,label3,label4,label5,label6);
        anchorPane.getChildren().add(layout);

        var listener = this;
        layout.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ActiveUser.user == null){
                    return;
                }
                if (ActiveUser.user.getUserType() == UserType.VOLUNTEER){
                    GameAdminController gaController = new GameAdminController();
                    baseController.showView("Game details", gaController, "/gameAdmin-view.fxml");
                    gaController.setGame(game);
                    gaController.setListener(listener);
                }
            }
        });

        return anchorPane;
    }
}
