package be.vghf;

import be.vghf.controllers.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VGHFApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        var controller = new BaseController();
        var setStage = controller.showView("VGHF Software", null, "/base-view.fxml");
        assert setStage != null;
        setStage.setMaximized(true);
    }

    public static void main(String[] args) {

        launch();
    }
}