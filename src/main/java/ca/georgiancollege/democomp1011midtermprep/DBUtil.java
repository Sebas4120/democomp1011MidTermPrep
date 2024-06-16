package ca.georgiancollege.democomp1011midtermprep;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DBUtil {

    private String connectionString, username, password, database;
    private String table;

    //These are protected because I want to allow the user to have a direct access to them in the
    // subclasses
    protected Connection connection;
    protected Statement statement;
    protected PreparedStatement preparedStatement;

    //Constructor 1
    public DBUtil(String database) {
        this.database = database;
        connectionString = "jdbc:mysql://database-1.cr4g0gw0k10a.us-west-2.rds.amazonaws.com:3306";
        username = "admin";
        password = "Narangita412.";

        try{
//            password = Files.readString(Path.of("src/main/resources/ca/georgiancollege/democomp1011midtermprep/data/.pwd"));
            connection = DriverManager.getConnection(
                    connectionString + "/" + database,
                    username, password);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    //Con este contructor se creo:
    //    public class BaseModel extends DBUtil{
    //        public BaseModel(){
    //            super("WEEK6", "StringAndStringBuilder");
    //        }
    //    }
    public DBUtil(String database, String table){
        this(database);
        setTable(table);
    }

    //Getters and Setters
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

//El método queryExec en la clase DBUtil está diseñado para ejecutar comandos SQL
// que no devuelven un conjunto de resultados (es decir, comandos UPDATE, DELETE,
// INSERT, y cualquier otro tipo de comando SQL que modifique la base de datos pero
// no devuelva filas).

    public void queryExec(String sql) throws Exception{
        statement = connection.createStatement();
        statement.execute(sql);
    }


//El método queryResult en la clase DBUtil está diseñado para ejecutar consultas SQL
// que devuelven un conjunto de resultados (es decir, consultas SELECT). Este método
// utiliza un PreparedStatement para ejecutar la consulta y devolver un ResultSet que
// contiene los datos resultantes.

    public ResultSet queryResult(String sql) throws SQLException{

        preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

//TO GET INFORMATION FROM AN SPECIFIC ROW OF A TABLE

    //El método getRowById está diseñado para obtener una fila específica de una
    // tabla de base de datos utilizando el id de esa fila.
    public ResultSet getRowById(String table, int id) throws SQLException{

//        Preparar la Consulta SQL

        //Llama al método prepareStatement en el objeto connection
        // (una instancia de Connection que representa la conexión
        // a la base de datos).
        preparedStatement = connection.prepareStatement("select * from "+table+" where id = ?");
        //Este método establece el valor del primer (y en este caso, único) parámetro en
        // la consulta preparada. El 1 indica la posición del parámetro en la consulta
        // (el primer ?), y id es el valor que se establece.
        preparedStatement.setInt(1, id);
        //Este método ejecuta la consulta SQL y devuelve un ResultSet
        // que contiene los resultados de la consulta.
        return preparedStatement.executeQuery();
    }

    public ResultSet deleteRowById(String table, int id) throws SQLException{

        preparedStatement = connection.prepareStatement("delete from "+table+" where id = ?");
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }


    public ResultSet getAllRows(String table) throws SQLException {
        return queryResult("select * from " + table);
    }
    public ResultSet getAllRowsOrderBy(String table, String column, String direction) throws SQLException {
        return queryResult("select * from " + table + " order by " + column + " " + direction);
    }



}