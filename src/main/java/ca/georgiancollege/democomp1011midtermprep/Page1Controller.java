package ca.georgiancollege.democomp1011midtermprep;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Page1Controller extends BaseController {


    @FXML
    private ComboBox<String> cb_datatype, cb_method;

    @FXML
    private Button history, submit, clear;

    //This onChangeOptions is an arraylist, whatever the user chooses String or StringBuilder
    //The OnChangeOptions will give them the methods for the specific datatype
    //in this list all the methods will be stored, methods from 'String' or 'StringBuilder'
    private ArrayList<String> onChangeOptions = new ArrayList<>();

    @FXML
    private TextField stats_stringbuilder, stats_strings, stats_total,tb_arguments, tb_content, tb_results;

    private Model model = new Model();

    //Setting the title and view file for the controller
    //Constructor where we pass in the title and the viewfile that the controller will control
    public Page1Controller(String title, String viewFile) {
        super(title, viewFile);
    }

    //Los totales
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
            //Esto es para la memoria de cuantos String y StringBuilder ya hay en la DB
            //Para que aparezcan la cantidad de 'String' y 'StringBuilder' que hay en la memoria
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

                //Si el usuario elige 'String' se incrementa el total de StringQueries
                //el equalsIgnoreCase es para que no importe si el usuario escribe 'String' o 'string'
                if(cb_datatype.getSelectionModel().getSelectedItem().equalsIgnoreCase("string"))
                    totalStringQueries++;
                else
                    //Si el usuario elige 'StringBuilder' se incrementa el total de StringBuilderQueries
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
                //CHANGING SCENES
                //Instantiate an object of the new controller 'Page2Controller'
                Page2Controller controller = new Page2Controller("Page 2", "page2");
                controller.setLastQuery(model.toString());
                System.out.println(model.toString());
                controller.openPage();
            }
            catch (Exception ex){
                displayErrorAlert("Page Error", "Could not open Page 2: " + ex.getMessage());
                System.out.println(ex);
            }
        });

        cb_datatype.setOnAction(event -> {

            //Con esto se registra lo que ha seleccionado el usuario
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