package be.vghf.controllers;
import be.vghf.domain.Location;
import be.vghf.domain.User;
import be.vghf.enums.LocationType;
import be.vghf.repository.LocationRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.*;


public class EventsController implements Controller{
    private BaseController baseController;
    @FXML private TextField locationsSearchText;
    @FXML private TableView<Location> locationsTableView;
    @FXML private ComboBox<String> locationfilterBox;
    private static SessionFactory sessionFactory;
    private String currentFilter;
    @FXML public void initialize(){
        //fill table with all locations on start up
        LocationRepository lr = new LocationRepository();
        var results = lr.getAll();
        locationsTableView.setItems(FXCollections.observableArrayList(results));

       //combobox init
        locationfilterBox.setPromptText("Filter");
        locationfilterBox.getItems().add("All");
        locationfilterBox.getItems().add("Type");
        locationfilterBox.getItems().add("Street name");
        currentFilter = "All";

        //link the table with the right datamembers of the different classes
        TableColumn<Location, String> streetColumn = new TableColumn<>("Street name");
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("streetName"));

        TableColumn<Location, String> houseNumberColumn = new TableColumn<>("House number");
        houseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));

        TableColumn<Location, String> busColumn = new TableColumn<>("Bus");
        busColumn.setCellValueFactory(new PropertyValueFactory<>("bus"));

        TableColumn<Location, String> postalCodeColumn = new TableColumn<>("Postal code");
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Location, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Location, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Location, String> ownerColumn = new TableColumn<>("Owner");
        ownerColumn.setCellValueFactory(cellData -> {
            User owner = cellData.getValue().getOwner();
            return new SimpleStringProperty(owner != null ? owner.getUserName() : "");
        });

        TableColumn<Location, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(celldata ->{
            LocationType type = celldata.getValue().getLocationType();
            return new SimpleStringProperty(type.toString());
        });

        locationsTableView.getColumns().addAll( typeColumn, streetColumn, houseNumberColumn, busColumn, postalCodeColumn, countryColumn, ownerColumn);
    }
    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }
    @Override
    public void setListener(Controller controller){
        //no listener needed here
    }

    @FXML protected void handleSearch(KeyEvent event) throws IOException {
        if(event.getCode() != KeyCode.ENTER){
            return;
        }

        String searchText = locationsSearchText.getText();
        String[] wordsArray = searchText.split("\\s+");
        List<Location> results = null;
        if(wordsArray == new String[]{""}) {
            LocationRepository lr = new LocationRepository();
            results = lr.getAll();
        }

        if(currentFilter == "All"){
            results = queryWithoutOrAllFilter(wordsArray);
        }
        else if(currentFilter == "Type"){
            results = queryWithTypeFilter(wordsArray);
        }
        else if(currentFilter == "Street name"){
            results = queryWithStreetFilter(wordsArray);
        }

        locationsTableView.setItems(FXCollections.observableArrayList(results));
    }

    @FXML protected void handleFilterChange(ActionEvent event){
        currentFilter = locationfilterBox.getValue();
        if(currentFilter == "All"){
            System.out.println("selectedFilter: " + currentFilter);
        }
        else if(currentFilter == "Type"){
            System.out.println("selectedFilter: " + currentFilter);
        }
        else if(currentFilter == "Street name"){
            System.out.println("selectedFilter: " + currentFilter);
        }
    }

    private List<Location> queryWithoutOrAllFilter(String[] wordsArry){
        Set<Location> locations = new HashSet<>();

        var results = queryWithStreetFilter(wordsArry);
        results.addAll(queryWithTypeFilter(wordsArry));

        locations.addAll(results);
        return new ArrayList<>(locations);
    }

    //TODO: Laat deze query werken
    private List<Location> queryWithTypeFilter(String[] wordsArray){
        LocationRepository lr = new LocationRepository();
        var results = new ArrayList<Location>();
        return results;
    }
    private List<Location> queryWithStreetFilter(String[] wordsArray) {
        LocationRepository lr = new LocationRepository();
        var results = lr.getLocationByStreet(wordsArray);
        return results;
    }
}
