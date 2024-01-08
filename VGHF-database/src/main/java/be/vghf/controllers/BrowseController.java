package be.vghf.controllers;

import antlr.StringUtils;
import be.vghf.domain.Console;
import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.ConsoleRepository;
import be.vghf.repository.GameRepository;
import com.sun.source.tree.Tree;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.CheckTreeView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BrowseController implements Controller{
    private BaseController baseController;
    @FXML private TextField gameSearchText;
    @FXML private TilePane gamesTableView;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private AnchorPane gamesTab;

    @FXML private TabPane tabPane;
    @FXML private AnchorPane consoleTab;
    private static SessionFactory sessionFactory;
    private String currentFilter;
    @FXML public void initialize(){
        GameRepository gr = new GameRepository();
        var games = gr.getAll();

        showGamesInTileView(games);

        filterComboBox.setPromptText("Filter");
        filterComboBox.getItems().add("All");
        filterComboBox.getItems().add("Title");
        filterComboBox.getItems().add("Console");

        currentFilter = "All";

        filterComboBox.onActionProperty().set(this::handleFilterChange);
        gameSearchText.setOnKeyReleased(event -> {
            try {
                handleSearch(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ConsoleRepository cr = new ConsoleRepository();
        var consoles = cr.getAll();
        setConsolesInConsolePane(consoles);

        tabPane.resize(1920,1080);

    }

    private void setConsolesInConsolePane(List<Console> consoles) {
        consoleTab.getChildren().clear();
        TreeItem<String> root = new TreeItem<>("Consoles");

        for (var console : consoles){
            TreeItem<String> consoleRoot = new TreeItem<>("Console: " + console.getConsoleName());
            consoleRoot.getChildren().addAll(
                new TreeItem<>("Type: " + console.getConsoleType()),
                new TreeItem<>("Company: " + console.getCompany().getCompanyName()),
                new TreeItem<>("Production: " + console.getReleaseYear() + " - " + ((console.getDiscontinuationYear() == "") ? "N.A" : console.getDiscontinuationYear())),
                new TreeItem<>("Units sold (mill): " + console.getUnitsSoldInMillions()),
                new TreeItem<>("Remarks: " + console.getRemarks())
            );

            root.getChildren().add(consoleRoot);
        }
        TreeView<String> treeView = new TreeView<>(root);

        consoleTab.resize(tabPane.getWidth(), tabPane.getHeight());
        consoleTab.getChildren().add(treeView);
        AnchorPane.setBottomAnchor(treeView, 0.0);
        AnchorPane.setLeftAnchor(treeView, 0.0);
        AnchorPane.setRightAnchor(treeView, 0.0);
        AnchorPane.setTopAnchor(treeView, 0.0);
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @FXML protected void handleSearch(KeyEvent event) throws IOException {
        if(event.getCode() != KeyCode.ENTER){
            return;
        }

        String searchText = gameSearchText.getText();
        String[] wordsArray = searchText.split("\\s+");
        List<Game> results = null;

        if(currentFilter == "All"){
            results = queryWithoutOrAllFilter(wordsArray);
        }
        else if(currentFilter == "Title"){
            results = queryWithTitleFilter(wordsArray);
        }
        else if(currentFilter == "Console"){
            results = queryWithConsoleFilter(wordsArray);
        }

        showGamesInTileView(results);

        //System.out.println("searchText: " + searchText);
    }

    @FXML protected void handleFilterChange(ActionEvent event){
        currentFilter = filterComboBox.getValue();
        if(currentFilter == "All"){
            System.out.println("selectedFilter: " + currentFilter);
        }
        else if(currentFilter == "Title"){
            System.out.println("selectedFilter: " + currentFilter);
        }
        else if(currentFilter == "Console"){
            System.out.println("selectedFilter: " + currentFilter);
        }
    }

    private List<Game> queryWithoutOrAllFilter(String[] wordsArray){
        Set<Game> games = new HashSet<>();

        var results = queryWithConsoleFilter(wordsArray);
        results.addAll(queryWithTitleFilter(wordsArray));

        games.addAll(results);
        return new ArrayList<>(games);
    }
    private List<Game> queryWithTitleFilter(String[] wordsArray){
        GameRepository gr = new GameRepository();
        var results = gr.getGameByName(wordsArray);
        return results;
    }

    private List<Game> queryWithConsoleFilter(String[] wordsArray) {
        Set<Game> games = new HashSet<>();

        ConsoleRepository cr = new ConsoleRepository();
        var consoles = cr.getConsoleByName(wordsArray);
        for(Console console : consoles){
            games.addAll(console.getGames());
        }
        return new ArrayList<>(games);
    }

    private String consoleSetToString(Set<Console> list){
        return list.stream().map(Console::getConsoleName).collect(Collectors.joining(", "));
    }

    private void showGamesInTileView(List<Game> games) {

        try {
            gamesTableView.getChildren().clear();
            gamesTableView.setVgap(10);
            gamesTableView.setHgap(10);
            gamesTableView.setPrefColumns( gamesTableView.getParent().getBoundsInParent().getWidth() == 0 ? 4 : ((int) gamesTableView.getParent().getBoundsInParent().getWidth()/200) - 1);

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
        Label label3 = new Label("Consoles: " + consoleSetToString(game.getConsoles()));
        Label label4 = new Label("Location: " + game.getCurrentLocation().toString());
        Label label5 = new Label("Genre: " + game.getGenre());

        layout.getChildren().add(label1);
        layout.getChildren().add(label2);
        layout.getChildren().add(label3);
        layout.getChildren().add(label4);
        layout.getChildren().add(label5);

        anchorPane.getChildren().add(layout);
        layout.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ActiveUser.user == null){
                    return;
                }
                if (ActiveUser.user.getUserType() == UserType.VOLUNTEER){
                    try {
                        GameAdminController gaController = new GameAdminController();
                        baseController.createNewWindow("Game details", gaController, "/gameAdmin-view.fxml");
                        gaController.setGame(game);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        return anchorPane;
    }
}
