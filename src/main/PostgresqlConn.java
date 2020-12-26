package main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostgresqlConn{
    protected String homeDirectory = "E:/OneDrive - Hanoi University of Science and Technology/Documents/Eclipse Projects/ProjectI/src/data/";
    protected static Connection connection;
    protected JSONObject jsonObject;
    protected JSONParser parser;
    protected static String url;
    protected static Map<String, String> login;

    public PostgresqlConn(){
        /***/
        login = new LinkedHashMap<>();
        parser = new JSONParser();
        try{
            Object object = parser.parse(new FileReader(homeDirectory + "databaselogin.json"));
            jsonObject = (JSONObject) object;
            login.put("hostname", (String) jsonObject.get("hostname"));
            login.put("port", (String) jsonObject.get("port"));
            login.put("user", (String) jsonObject.get("user"));
            login.put("password", (String) jsonObject.get("password"));
            login.put("database", (String) jsonObject.get("database"));
        }catch(Exception e){
            e.printStackTrace();
        }
        /** url */
        url = "jdbc:postgresql://" + login.get("hostname") + ":" + login.get("port") + "/" + login.get("database");
    }

    public Connection getConnection() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url, login.get("user"), login.get("password"));
        }
        return connection;
    }


}
