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
import javafx.scene.control.TextField;
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


    //Events en Locations
    //TODO: Gitte: Toevoegen van een couch db server die als key de naam van een expo bevat met dan een korte bio en link naar de website

    @FXML
    public void initialize() {
        usersButton.setVisible(false);

        changeSubscene("/browse-view.fxml", new BrowseController());

        ActiveUser.user = null;
    }
    @FXML protected void handleAccountButtonPressed(ActionEvent event) throws IOException {
        if(ActiveUser.user == null){
            showView("Account Manager", new AccountController(),"/loginOrRegister-view.fxml");
        }else{
            showView("Account Manager", new LogoutController(),"/logout-view.fxml");
        }
    }
    @FXML protected void handleBrowseButtonPressed(ActionEvent event) throws IOException {
        BrowseController controller = new BrowseController();
        changeSubscene("/browse-view.fxml", controller);
        controller.update();

    }
    @FXML protected void handleEventsButtonPressed(ActionEvent event) throws IOException{
        EventsController controller = new EventsController();
        changeSubscene("/events-locations-view.fxml", controller);
        controller.update();
    }
    @FXML protected void handleUsersButtonPressed(ActionEvent actionEvent) {
        changeSubscene("/users-view.fxml", new UsersController());
    }
    @FXML protected void handleLoansButtonPressed(ActionEvent actionevent){
        User user = ActiveUser.user;
        var loanedItemsController = new LoanedItemsController();
        baseController.changeSubscene("/userLoanedItems-view.fxml", loanedItemsController);
        loanedItemsController.setUser(user);
        loanedItemsController.setListener(this);
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
            Button addGameButton = (Button) subScene.lookup("#addGameButton");
            if(addGameButton != null){ addGameButton.setVisible(false);}

            Button addConsoleButton = (Button) subScene.lookup("#addConsoleButton");
            if(addConsoleButton != null){addConsoleButton.setVisible(false);}
            Button editConsoleButton = (Button) subScene.lookup("#editConsoleButton");
            if(editConsoleButton != null){editConsoleButton.setVisible(false);}

            Button addCompanyButton = (Button) subScene.lookup("#addCompanyButton");
            if(addCompanyButton != null){addCompanyButton.setVisible(false);}
            Button editCompanyButton = (Button) subScene.lookup("#editCompanyButton");
            if(editCompanyButton != null){editCompanyButton.setVisible(false);}

            Button addLocationButton = (Button) subScene.lookup("#addLocationButton");
            if(addLocationButton != null){addLocationButton.setVisible(false);}
            return;

        }else if (ActiveUser.user.getUserType().equals(UserType.VOLUNTEER)) {
            usersButton.setVisible(true);

            Button addGameButton = (Button) subScene.lookup("#addGameButton");
            if(addGameButton != null){ addGameButton.setVisible(true);}


            TextField searchBar = (TextField) subScene.lookup("#gameSearchText");
            if(searchBar != null){AnchorPane.setLeftAnchor(searchBar, 60.0);}

            Button addConsoleButton = (Button) subScene.lookup("#addConsoleButton");
            if(addConsoleButton != null){addConsoleButton.setVisible(true);}
            Button editConsoleButton = (Button) subScene.lookup("#editConsoleButton");
            if(editConsoleButton != null){editConsoleButton.setVisible(true);}

            Button addCompanyButton = (Button) subScene.lookup("#addCompanyButton");
            if(addCompanyButton != null){addCompanyButton.setVisible(true);}
            Button editCompanyButton = (Button) subScene.lookup("#editCompanyButton");
            if(editCompanyButton != null){editCompanyButton.setVisible(true);}

            Button addLocationButton = (Button) subScene.lookup("#addLocationButton");
            if(addLocationButton != null){addLocationButton.setVisible(false);}
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
