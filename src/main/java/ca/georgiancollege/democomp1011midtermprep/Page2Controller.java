package ca.georgiancollege.democomp1011midtermprep;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class Page2Controller extends BaseController {

    @FXML
    private Button refresh;
    @FXML
    private Label total1, total2, last;

    private String lastQuery = "N/A";


    @FXML
    private TableView<Model> table;

    private Model model = new Model();

    public Page2Controller(String title, String viewFile) {
        super(title, viewFile);
    }

    public String getLastQuery() {
        return lastQuery;
    }

    public void setLastQuery(String lastQuery) {
        this.lastQuery = lastQuery;
    }

    @FXML
    private void initialize(){

        try {
            int totalStringQuries = model.getQueryTotal("string");
            int totalStringBuilderQueries = model.getQueryTotal("stringbuilder");
            total1.setText(totalStringQuries + "");
            total2.setText(totalStringBuilderQueries + "");
            last.setText(getLastQuery());


        }
        catch (Exception ex){
            displayErrorAlert("Total Retrieval Error", ex.getMessage());
        }

    }
}