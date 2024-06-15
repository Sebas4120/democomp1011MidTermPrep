package ca.georgiancollege.democomp1011midtermprep;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

//Base for all controllers - abstract class
public abstract class BaseController {

    //What do I need to create a screen?
    //1. Stage
    //2. View File
    //3. Title

    private Stage stage;
    private String viewFile, title;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public BaseController(String title, String viewFile){
        this.title = title;
        this.viewFile = viewFile;
    }
    void openPage() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(viewFile + "-view.fxml"));

        fxmlLoader.setController(this);

        Scene scene = new Scene(fxmlLoader.load());
        if(stage == null)
            setStage(new Stage());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public  void displaySuccessAlert(String title, String message){
        displayAlert(Alert.AlertType.INFORMATION, title, message);
    }
    public  void displayErrorAlert(String title, String message){
        displayAlert(Alert.AlertType.ERROR, title, message);
    }
    private void displayAlert(Alert.AlertType type, String title, String message){

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();

    }

}