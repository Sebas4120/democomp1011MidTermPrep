package ca.georgiancollege.democomp1011midtermprep;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Page1Controller extends BaseController {


    @FXML
    private ComboBox<String> cb_datatype, cb_method;

    @FXML
    private Button history, submit, clear;

    private ArrayList<String> onChangeOptions = new ArrayList<>();
    @FXML
    private TextField stats_stringbuilder, stats_strings, stats_total,tb_arguments, tb_content, tb_results;

    private Model model = new Model();

    public Page1Controller(String title, String viewFile) {
        super(title, viewFile);
    }

    private int totalStringQueries, totalStringBuilderQueries;

    private void initDataTypes(){
        cb_datatype.getItems().add("String");
        cb_datatype.getItems().add("StringBuilder");
    }
    private void updatePageStats(){
        stats_strings.setText(totalStringQueries + "");
        stats_stringbuilder.setText(totalStringBuilderQueries + "");
        stats_total.setText((totalStringQueries + totalStringBuilderQueries) + "");

    }
    @FXML
    private void initialize(){

        initDataTypes();

        try {
            totalStringQueries = model.getQueryTotal("string");
            totalStringBuilderQueries = model.getQueryTotal("stringbuilder");
            updatePageStats();
        }
        catch (Exception ex){
            displayErrorAlert("Total Retrieval Error", ex.getMessage());
            System.out.println(ex);
        }

        submit.setOnAction(event -> {
            if(cb_datatype.getSelectionModel().getSelectedIndex() == -1) {
                displayErrorAlert("Choose Data Type", "Please choose Data Type before submitting");
                return;
            }
            String dataType = cb_datatype.getSelectionModel().getSelectedItem();
            String content = tb_content.getText();
            String arguments = tb_arguments.getText();
            String method = cb_method.getSelectionModel().getSelectedItem();

            try{
                model.run(dataType, content, method, arguments);
                tb_results.setText(model.getResults());
                displaySuccessAlert("Success", "Form Successfully Submitted to Database");

                if(cb_datatype.getSelectionModel().getSelectedItem().equalsIgnoreCase("string"))
                    totalStringQueries++;
                else
                    totalStringBuilderQueries++;

                updatePageStats();
            }
            catch (Exception ex){
                displayErrorAlert("Submission Error", ex.getMessage());
                System.out.println(ex);
            }

        });

        history.setOnAction(event -> {
            try{
                Page2Controller controller = new Page2Controller("Page 2", "page2");
                controller.setLastQuery(model.toString());
                controller.openPage();
            }
            catch (Exception ex){
                displayErrorAlert("Page Error", "Could not open Page 2: " + ex.getMessage());
                System.out.println(ex);
            }
        });

        cb_datatype.setOnAction(event -> {

            String dataTypeSelected = cb_datatype.getSelectionModel().getSelectedItem();
            onChangeOptions.clear();


            if(dataTypeSelected.equalsIgnoreCase("string")){
                onChangeOptions.add("contains");
                onChangeOptions.add("endsWith");
                onChangeOptions.add("length");
                onChangeOptions.add("startsWith");
                onChangeOptions.add("toUpperCase");
                onChangeOptions.add("toLowerCase");
            }
            else if(dataTypeSelected.equalsIgnoreCase("stringbuilder")){
                onChangeOptions.add("append");
                onChangeOptions.add("capacity");
                onChangeOptions.add("deleteCharAt");
                onChangeOptions.add("insert");
            }

            cb_method.getItems().clear();
            cb_method.getItems().addAll(onChangeOptions);
            cb_method.getSelectionModel().selectFirst();
        });

        clear.setOnAction(event -> {
            tb_arguments.clear();
            tb_content.clear();
            tb_results.clear();
            cb_method.getSelectionModel().selectFirst();
            cb_datatype.getSelectionModel().selectFirst();

        });
    }
}