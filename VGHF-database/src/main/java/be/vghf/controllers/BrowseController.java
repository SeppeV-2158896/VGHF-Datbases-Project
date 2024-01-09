package be.vghf.controllers;

import be.vghf.domain.*;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.ConsoleRepository;
import be.vghf.repository.Dev_companyRepository;
import be.vghf.repository.GameRepository;
import be.vghf.repository.GenericRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BrowseController implements Controller{
    private BaseController baseController;
    private GameRepository gameRepository;
    @FXML private TextField gameSearchText;
    @FXML private TilePane gamesTableView;
    @FXML private ComboBox<String> gamesFilterComboBox;
    @FXML private AnchorPane gamesTabPane;
    @FXML private TableView<Dev_company> companiesTableView;
    @FXML private TabPane tabPane;
    @FXML private AnchorPane consoleTab;
    @FXML private TextField companySearchText;
    @FXML private ComboBox<String> companiesFilterComboBox;
    @FXML private TextField consoleQueryField;
    @FXML private Button addGameButton;

    @FXML private Tab gamesTab;

    //Games:
    //TODO: Pex: Als je als VOLUNTEER op een game klikt moet je de locatie, owner en homebase kunnen aanpassen door te kiezen uit een lijst waarin je kan filteren
    //TODO: Pex: VOLUNTEER moet game kunnen toevoegen

    //Consoles:
    //TODO: Pex: VOLUNTEER moet consoles kunnen aanpassen, toevoegen en verwijderen

    //Companies:
    //TODO: Pex: VOLUNTEER moet consoles kunnen aanpassen, toevoegen en verwijderen

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
        baseController.setListener(this);
    }

    @Override
    public void setListener(Controller controller){
        //no listener needed here
    }

    @FXML public void initialize(){
        tabPane.resize(1920,1080);
        initializeGamesBrowser();
        initializeCompanyBrowser();
        initializeConsoleBrowser();

    }

    private void initializeGamesBrowser(){
        gameRepository = new GameRepository();
        var games = gameRepository.getAll();

        addGameButton.setVisible(false);

        showGamesInTileView(new HashSet<>(games));

        gamesFilterComboBox.setPromptText("Filter");
        gamesFilterComboBox.getItems().add("All");
        gamesFilterComboBox.getItems().add("Title");
        gamesFilterComboBox.getItems().add("Console");

        gameSearchText.setOnKeyReleased(this::handleGameSearch);
        companySearchText.setOnKeyReleased(this::handleCompanySearch);
    }

    public void initializeGamesWithLocation(Location location){
        showGamesInTileView(new GameRepository().getAllByLocation(location));
    }

    private void initializeCompanyBrowser(){
        companiesFilterComboBox.setPromptText("Filter");
        companiesFilterComboBox.getItems().add("All");
        companiesFilterComboBox.getItems().add("Title");
        companiesFilterComboBox.getItems().add("Location");

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

        companiesTableView.setItems(FXCollections.observableList(new Dev_companyRepository().getAll()));
        companiesTableView.setEditable(false);
        companiesTableView.getColumns().addAll(nameCol, websiteCol, emailCol, addressCol);

        companiesTableView.setOnMouseReleased(event -> {
            showGamesInTileView(companiesTableView.getSelectionModel().getSelectedItem().getGames());
            tabPane.getSelectionModel().select(gamesTab);
        });
    }

    private void initializeConsoleBrowser(){
        ConsoleRepository cr = new ConsoleRepository();
        var consoles = cr.getAll();
        setConsolesInConsolePane(consoles);

        consoleQueryField.setOnKeyReleased(this::handleConsoleSearch);
    }

    @FXML protected void handleGameSearch(KeyEvent event) {
        if(event.getCode() != KeyCode.ENTER){
            return;
        }

        String currentFilter = gamesFilterComboBox.getValue();

        String gameSearchText = this.gameSearchText.getText();
        String[] gameSearch = gameSearchText.split("\\s+");
        Set<Game> gameResults = null;

        if(currentFilter == "All" || currentFilter == null){
            gameResults = queryGameWithoutOrAllFilter(gameSearch);
        }
        else if(currentFilter == "Title"){
            gameResults = queryGameWithTitleFilter(gameSearch);
        }
        else if(currentFilter == "Console"){
            gameResults = queryGameWithConsoleFilter(gameSearch);
        }

        showGamesInTileView(gameResults);
    }

    @FXML protected void handleAddGame(ActionEvent event){
        GameAdminController gameAdminController = new GameAdminController();
        gameAdminController.setNewGame(true);
        gameAdminController.setListener(this);
        baseController.showView("Create new game", gameAdminController, "/gameAdmin-view.fxml");
    }

    @FXML private void handleCompanySearch(KeyEvent event) {
        if(event.getCode() != KeyCode.ENTER){
            return;
        }
        String currentFilter = companiesFilterComboBox.getValue();

        String companySearchText = this.companySearchText.getText();
        String[] companySearch = companySearchText.split("\\s+");
        Set<Dev_company> companyResults = new HashSet<>();

        if(Objects.equals(currentFilter, "All") || Objects.equals(currentFilter, null)){
            companyResults = queryCompaniesWithoutOrAllFilter(companySearch);
        }
        else if(Objects.equals(currentFilter, "Name")){
            companyResults = queryCompaniesWithTitleFilter(companySearch);
        }
        else if(Objects.equals(currentFilter, "Location")){
            companyResults = queryCompaniesWithLocationFilter(companySearch);
        }

        companiesTableView.setItems(FXCollections.observableArrayList(companyResults));
    }

    private Set<Game> queryGameWithoutOrAllFilter(String[] wordsArray){
        var results = queryGameWithConsoleFilter(wordsArray);
        results.stream().filter((queryGameWithTitleFilter(wordsArray))::contains);
        return results.stream().filter((queryGameWithTitleFilter(wordsArray))::contains).collect(Collectors.toSet());
    }

    private Set<Game> queryGameWithTitleFilter(String[] wordsArray){
        var results = gameRepository.getGameByName(wordsArray);
        return results;
    }

    private Set<Game> queryGameWithConsoleFilter(String[] wordsArray) {
        Set<Game> games = new HashSet<>();

        ConsoleRepository cr = new ConsoleRepository();
        var consoles = cr.getConsoleByName(wordsArray);
        for(Console console : consoles){
            games.addAll(console.getGames());
        }
        return games;
    }

    private Set<Game> queryGameWithLocationFilter(String[] wordsArray){
        var results = gameRepository.getGameByName(wordsArray);
        return results;
    }

    private Set<Dev_company> queryCompaniesWithoutOrAllFilter(String[] wordsArray){

        var results = queryCompaniesWithTitleFilter(wordsArray);
        results.addAll(queryCompaniesWithLocationFilter(wordsArray));

        return results;
    }

    private Set<Dev_company> queryCompaniesWithTitleFilter(String[] wordsArray){
        return new Dev_companyRepository().getCompaniesByName(wordsArray);
    }

    private Set<Dev_company> queryCompaniesWithLocationFilter(String[] wordsArray) {
        return new Dev_companyRepository().getCompanyByLocation(wordsArray);
    }

    private void handleConsoleSearch(KeyEvent keyEvent) {
        if(keyEvent.getCode() != KeyCode.ENTER){
            return;
        }

        String consoleSearchText = this.consoleQueryField.getText();
        String[] consoleSearch = consoleSearchText.split("\\s+");
        setConsolesInConsolePane(new ConsoleRepository().getConsoleByName(consoleSearch));
    }

    private void showGamesInTileView(Set<Game> games) {

        try {
            gamesTableView.getChildren().clear();
            gamesTableView.setVgap(10);
            gamesTableView.setHgap(10);
            gamesTableView.setPrefColumns( gamesTableView.getParent().getBoundsInParent().getWidth() == 0 ? 4 : (int) gamesTableView.getParent().getBoundsInParent().getWidth()/200);

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

        layout.getChildren().add(label1);
        layout.getChildren().add(label2);
        layout.getChildren().add(label3);
        layout.getChildren().add(label4);
        layout.getChildren().add(label5);

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

    public void updateGameDetails(Game newGame){
        Set<Game> games = Set.copyOf(gameRepository.getAll());
        showGamesInTileView(games);
    }

    private void setConsolesInConsolePane(List<Console> consoles) {
        consoleTab.getChildren().clear();

        VBox treeView = new VBox();
        treeView.setSpacing(10);
        VBox.setVgrow(treeView, Priority.ALWAYS);

        for (var console : consoles){
            AnchorPane pane = new AnchorPane();
            pane.setPrefWidth(1920);

            TreeItem<String> consoleRoot = new TreeItem<>("Console: " + console.getConsoleName());
            consoleRoot.getChildren().addAll(
                    new TreeItem<>("Type: " + console.getConsoleType()),
                    new TreeItem<>("Company: " + console.getCompany().getCompanyName()),
                    new TreeItem<>("Production: " + console.getReleaseYear() + " - " + ((console.getDiscontinuationYear() == "") ? "N.A" : console.getDiscontinuationYear())),
                    new TreeItem<>("Units sold (mill): " + console.getUnitsSoldInMillions()),
                    new TreeItem<>("Remarks: " + console.getRemarks())
            );

            TreeView<String> consoleRootView = new TreeView<>(consoleRoot);
            consoleRootView.setPrefHeight(60);
            consoleRootView.setPrefWidth(1920);

            consoleRootView.setOnMouseReleased(event -> {
                if (event.getClickCount() == 2) {
                    showGamesInTileView(console.getGames());
                    tabPane.getSelectionModel().select(gamesTab);
                    gameSearchText.setText(console.getConsoleName());
                }
            });
            pane.getChildren().add(consoleRootView);
            treeView.getChildren().add(pane);
        }

        consoleTab.resize(consoleTab.getWidth(), consoleTab.getHeight());
        consoleTab.getChildren().add(treeView);
        AnchorPane.setBottomAnchor(treeView, 0.0);
        AnchorPane.setLeftAnchor(treeView, 0.0);
        AnchorPane.setRightAnchor(treeView, 0.0);
        AnchorPane.setTopAnchor(treeView, 0.0);


    }


}
