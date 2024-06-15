package ca.georgiancollege.democomp1011midtermprep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Page1Controller controller = new Page1Controller("Page 1", "page1");
        controller.setStage(stage);
        controller.openPage();
    }

    public static void main(String[] args) {
        launch();
    }
}