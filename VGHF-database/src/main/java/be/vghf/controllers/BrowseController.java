package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.domain.User;
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

import java.io.IOException;
import java.sql.Timestamp;

public class BrowseController implements Controller{
    private BaseController baseController;
    @FXML private TextField gameSearchText;
    @FXML private TableView<Game> gamesTableView;

    @FXML public void initialize(){
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
        if(event.getCode() == KeyCode.ENTER){
            String searchText = gameSearchText.getText();
            String[] wordsArray = searchText.split("\\s+");

            GameRepository gr = new GameRepository();
            var results = gr.getGameByName(wordsArray);

            gamesTableView.setItems(FXCollections.observableArrayList(results));

            System.out.println("searchText: " + searchText);
        }
    }
}
