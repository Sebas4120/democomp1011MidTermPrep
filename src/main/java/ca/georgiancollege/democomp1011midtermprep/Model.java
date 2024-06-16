package ca.georgiancollege.democomp1011midtermprep;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Model extends BaseModel {

    //private instance variables
    private String dataType, content, arguments, method, results;

    public Model(){}

    public void run(String dataType, String content, String method, String arguments) throws Exception {
        setDataType(dataType.toLowerCase());
        setArguments(arguments.toLowerCase());
        setMethod(method.toLowerCase());
        setContent(content);
        //We are going to generate the results by calling the method 'setResults'
        setResults();
        //We are going to insert the row into our database
        insertRow();
    }

    //INSERTAR LOS NUEVOS DATOS A LA BASE DE DATOS
    private void insertRow() throws SQLException {

        preparedStatement = connection.prepareStatement("insert into " + getTable()+
                " (datatype, content, method, args, result) " +
                "values (?, ?, ?, ?, ?)" );

        preparedStatement.setString(1, dataType);
        preparedStatement.setString(2, content);
        preparedStatement.setString(3, method);
        preparedStatement.setString(4, arguments);
        preparedStatement.setString(5, results);
        preparedStatement.execute();
    }
    public String getDataType() {
        return dataType;
    }

//Validation Method
    //Method to throw an error if the value is too short
    private void throwTooShortError(String title, String value, int minLength){
        if(value.length() < minLength)
            throw new IllegalArgumentException(title + "is too short. Needs to be at least "
                    + minLength + "characters");

    }
//Getters and Setters
    //Data Type does have restrictions for mimimum
    public void setDataType(String dataType) {
        throwTooShortError("Data Type", dataType, 5);
        this.dataType = dataType;
    }

    public String getContent() {
        return content;
    }
    //Content does have restrictions for mimimum
    public void setContent(String content) {
        throwTooShortError("Content", content, 5);
        this.content = content;
    }


    public String getArguments() {
        return arguments;
    }

    //Arguments does not have restrictions for mimimum
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getMethod() {
        return method;
    }
    //Method does have restrictions for mimimum
    public void setMethod(String method) {
        throwTooShortError("Method", method, 4);
        this.method = method;
    }


    private void setResults() {
        int index;

        System.out.println(dataType);
        System.out.println(method);

        switch (dataType){
            case "string":
                switch (method){
                    case "tolowercase":
                        results = content.toLowerCase();
                        break;
                    case "touppercase":
                        results = content.toUpperCase();
                        break;
                    case "contains":
                        results = content.contains(arguments) + "";
                        break;
                    case "length":
                        results = content.length() + "";
                        break;
                    case "endswith":
                        results = content.endsWith(arguments) + "";
                        break;
                    case "startswith":
                        //Se debe entender de dos formas, si es usuario ingresa:
                        // Palabra: Hello // Argumento: H ......length del argumento (pieces) =1
                        // Palabra: Hello // Argumento: H, 1 (LA PALABRA EMPIEZA CON h DESDE EL
                        // INDEX 1)........length del argumento (pieces) =2

                        String[] pieces = arguments.split(",");
                        if(pieces.length == 1)
                            results = content.startsWith(arguments) + "";
                        else if(pieces.length == 2){
                            index = Integer.parseInt(pieces[1]);
                            String search = pieces[0];
                            results = content.startsWith(search, index) + "";
                        }
                        break;
                }
                break;
            case "stringbuilder":
                StringBuilder sb = new StringBuilder(content);
                switch (method){
                    case "append":
                        results = sb.append(arguments).toString();
                        break;
                    case "insert":
                        String[] pieces = arguments.split(",");
                        //It is the index where you want to add the new argument
                        index = Integer.parseInt(pieces[0]);
                        //This is what you want to add
                        String add = pieces[1];
                        results = sb.insert(index, add).toString();
                        break;
                    case "capacity":
                        results = sb.capacity() + "";
                        break;
                    case "deletecharat":
                        index = Integer.parseInt(arguments);
                        results = sb.deleteCharAt(index).toString();
                        break;
                }
                break;
        }

        System.out.println("Final result = " + results);
    }

    public String getResults(){
        return results;
    }

    //This method is going to return the total number of rows in the database
    //This is going to get the total value depending on the datatype
    public int getQueryTotal(String dataType) throws SQLException {
        //Construcción de la Consulta SQL
        String sql = "select count(*) as num from " + getTable() +
                " where datatype = '" + dataType + "'";
        // Se asume que este método ejecuta la consulta SQL y devuelve un
        // ResultSet que contiene el resultado de la consulta.
        ResultSet resultSet = queryResult(sql);
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public String toString() {
        return "Model{" +
                "dataType='" + dataType + '\'' +
                ", content='" + content + '\'' +
                ", arguments='" + arguments + '\'' +
                ", method='" + method + '\'' +
                ", results='" + results + '\'' +
                '}';
    }
}