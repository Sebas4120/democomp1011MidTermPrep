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

    //CON ESTO ENLAZAMOS EL CONTROLLER CON EL VIEW CORRESPONDIENTE
    //In order to instantiate a BaseController object, we need to pass in a title and a viewFile
    public BaseController(String title, String viewFile){
        this.title = title;
        this.viewFile = viewFile;
    }

    void openPage() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.
                getResource(viewFile + "-view.fxml"));

        //"this" is refering To an object of this class

        //Esta línea de código establece el controlador para el archivo
        // FXML que se está cargando. En otras palabras, le dice al
        // FXMLLoader que utilice la instancia actual de BaseController
        // como el controlador para el archivo FXML que se está cargando.
        fxmlLoader.setController(this);

        Scene scene = new Scene(fxmlLoader.load());
        if(stage == null)
            setStage(new Stage());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //Method to display alerts
    //Sí, Alert.AlertType es una clase interna estática predefinida
    // en la clase Alert de JavaFX.

    //La clase Alert en JavaFX proporciona una forma de mostrar diferentes tipos de
    // cuadros de diálogo (diálogos de alerta) al usuario, como mensajes de información,
    // advertencias y errores.

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