package be.vghf.controllers;

import be.vghf.domain.Console;
import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.repository.ConsoleRepository;
import be.vghf.repository.GameRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.CheckTreeView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrowseController implements Controller{
    private BaseController baseController;
    @FXML private TextField gameSearchText;
    @FXML private TableView<Game> gamesTableView;
    @FXML private ComboBox<String> filterComboBox;
    private static SessionFactory sessionFactory;
    private String currentFilter;
    @FXML public void initialize(){
        //fill table with all games on start up
        GameRepository gr = new GameRepository();
        var results = gr.getAll();
        gamesTableView.setItems(FXCollections.observableArrayList(results));

        //combobox init
        filterComboBox.setPromptText("Filter");
        filterComboBox.getItems().add("All");
        filterComboBox.getItems().add("Title");
        filterComboBox.getItems().add("Console");
        currentFilter = "All";

        //link the table with the right datamembers of the different classes
        TableColumn<Game, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Game, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Game, String> ownerColumn = new TableColumn<>("Owner");
        ownerColumn.setCellValueFactory(cellData -> {
            User owner = cellData.getValue().getOwner();
            return new SimpleStringProperty(owner != null ? owner.getUserName() : "");
        });

        TableColumn<Game, String> currentLocationColumn = new TableColumn<>("Current Location");
        currentLocationColumn.setCellValueFactory(cellData -> {
            Location location = cellData.getValue().getCurrentLocation();
            return new SimpleStringProperty(location != null ? location.getStreetName() : "");
        });

        TableColumn<Game, String> homeBaseColumn = new TableColumn<>("Home Base Location");
        homeBaseColumn.setCellValueFactory(cellData -> {
            Location homeBase = cellData.getValue().getHomeBase();
            return new SimpleStringProperty(homeBase != null ? homeBase.getStreetName() : "");
        });

        gamesTableView.getColumns().addAll(titleColumn, genreColumn, ownerColumn, currentLocationColumn, homeBaseColumn);
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

        gamesTableView.setItems(FXCollections.observableArrayList(results));

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

    private List<Game> queryWithoutOrAllFilter(String[] wordsArry){
        Set<Game> games = new HashSet<>();

        var results = queryWithConsoleFilter(wordsArry);
        results.addAll(queryWithTitleFilter(wordsArry));

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
}
