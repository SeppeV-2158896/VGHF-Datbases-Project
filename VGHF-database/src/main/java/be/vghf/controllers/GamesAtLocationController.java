package be.vghf.controllers;

import be.vghf.domain.Game;
import be.vghf.domain.Location;
import be.vghf.enums.UserType;
import be.vghf.models.ActiveUser;
import be.vghf.repository.GameRepository;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.*;

public class GamesAtLocationController implements Controller {
    private BaseController baseController;
    @FXML private AnchorPane gamesAtLocationPane;
    @FXML private ScrollPane scrollPane;


    @FXML public void initialize(Location location){
        scrollPane.resize(1920,1080);
        setGamesInPane(location);
    }

    private void setGamesInPane(Location location) {
        Map<Location, List<Game>> homebaseMap = makeHomebaseMap(location);
        gamesAtLocationPane.getChildren().clear();
        TreeItem<String> root = new TreeItem<>("Games");
        var locations = homebaseMap.keySet();

        for (var loc : locations){
            TreeItem<String> locationRoot = new TreeItem<>("Homebase: "  + loc.toString());
            List<Game> games = homebaseMap.get(loc);
            for(var game: games) {
                TreeItem<String>  gameItem = new TreeItem<>("Game: " + game.getTitle());
                locationRoot.getChildren().add(gameItem);
            }
            root.getChildren().add(locationRoot);
        }
        root.setExpanded(true);
        TreeView<String> treeView = new TreeView<>(root);
        treeView.setPrefWidth(1920);
        gamesAtLocationPane.resize(scrollPane.getWidth(), scrollPane.getHeight());
        gamesAtLocationPane.getChildren().add(treeView);
        AnchorPane.setBottomAnchor(treeView, 0.0);
        AnchorPane.setLeftAnchor(treeView, 0.0);
        AnchorPane.setRightAnchor(treeView, 0.0);
        AnchorPane.setTopAnchor(treeView, 0.0);
    }

    private Map<Location, List<Game>> makeHomebaseMap(Location location){
        Map<Location, List<Game>> homebaseMap = new HashMap<>();
        GameRepository gr = new GameRepository();
        Set<Game> gameset = gr.getAllByLocation(location);

        for(var game : gameset){
            Location homebase = game.getHomeBase();
            if(homebaseMap == null){
                homebaseMap.put(homebase, new ArrayList<Game>());
                homebaseMap.get(homebase).add(game);
            }else if(homebaseMap.containsKey(homebase)){
                homebaseMap.get(homebase).add(game);
            }else{
                homebaseMap.put(homebase, new ArrayList<Game>());
                homebaseMap.get(homebase).add(game);
            }
        }
        return homebaseMap;
    }

    @Override
    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
        baseController.setListener(this);
    }

    @Override
    public void setListener(Controller controller) {

    }
}
