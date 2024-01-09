package be.vghf.controllers;

import be.vghf.domain.User;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController implements Controller{

    @FXML private AnchorPane baseContainer;
    @FXML private Text testTekst;
    @FXML private Button accountButton;
    @FXML private Button usersButton;
    @FXML private Button eventsButton;
    @FXML private Button browseButton;
    @FXML private Button loanedItemsButton;
    @FXML private AnchorPane subScene;

    private Controller listener;
    private BaseController baseController = this;

    //Loaned Items:

    //TODO: Gitte: ALS VOLUNTEER of CLIENT moet je eerst alle spellen zien die je hebt uitgeleend op datum en degene die nog uitgeleend zijn moeten een andere kleur hebben dan die wat zijn afgerond, ook een andere kleur voor openstaande boetes
    /*TODO: Seppe: Admin moet op een knop kunnen duwen die een nieuw window opent waarin hij games kan uitlenen en markeren als terug gebracht, let op volunteer
     * moet eerst zijn actieve locatie aangeven en dan hier de geleende spellen van zien, is onlogisch dat je spellen van andere locaties uitleend en verwerkt.
     * Als je als VOLUNTEER op een game klikt moet je de game kunnen uitlenen aan een gebruiker door het op zoeken van de volledige naam (voor + achternaam, zoals in de bib).
     * Ook een mogelijkheid om de totale openstaande boete op te vragen en te berekenen van nieuwe boetes
     */

    //Events en Locations

    //TODO: Gitte: Toevoegen van een couch db server die als key de naam van een expo bevat met dan een korte bio en link naar de website
    //TODO: Gitte: Gasten moeten lijst kunnen krijgen van de aanwezige spellen
    //TODO: Gitte: Toevoegen van filter (ComboBox voor type locatie) en queryfield voor locatie
    //TODO: Gitte: Volunteers moeten op een locatie kunnen klikken naar een treeview van alle spellen per home base


    @FXML
    public void initialize() {
        usersButton.setVisible(false);
        loanedItemsButton.setVisible(false);

        changeSubscene("/browse-view.fxml", new BrowseController());

        ActiveUser.user = null;
    }
    @FXML protected void handleAccountButtonPressed(ActionEvent event) throws IOException {
        showView("Account Manager", new AccountController(),"/loginOrRegister-view.fxml");
    }
    @FXML protected void handleBrowseButtonPressed(ActionEvent event) throws IOException {
        changeSubscene("/browse-view.fxml", new BrowseController());

    }
    @FXML protected void handleEventsButtonPressed(ActionEvent event) throws IOException{
        changeSubscene("/events-locations-view.fxml", new EventsController());
    }
    @FXML protected void handleUsersButtonPressed(ActionEvent actionEvent) {
        changeSubscene("/users-view.fxml", new UsersController());
    }
    public Stage showView(String title, Controller controller, String path){
        try {
            var loader = new FXMLLoader(BaseController.class.getResource(path));

            if (controller != null) {
                loader.setController(controller);
                controller.setBaseController(this);
            }
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            return stage;

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public void changeSubscene(String path, Controller controller){
        try {
            subScene.getChildren().clear();
            var loader = new FXMLLoader(BaseController.class.getResource(path));

            if (controller != null) {
                loader.setController(controller);
                controller.setBaseController(this);
            }
            Parent root = loader.load();

            subScene.getChildren().add(root);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void update() {

        if (ActiveUser.user == null) {
            usersButton.setVisible(false);
            loanedItemsButton.setVisible(false);

            return;
        }

        loanedItemsButton.setVisible(true);

        if (ActiveUser.user.getUserType().equals(UserType.VOLUNTEER)) {
            usersButton.setVisible(true);
        }
    }
    @Override
    public void setBaseController(BaseController baseController) {
    }
    @Override
    public void setListener(Controller listener) {
        this.listener = listener;
    }


}
