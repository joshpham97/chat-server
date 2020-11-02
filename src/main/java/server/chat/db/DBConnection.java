package server.chat.db;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection  {
    // Read config file
    private static final String CONFIG_FILE = "src\\main\\config.json";
    private static Object obj;
    private static JSONObject jo;
    static {
        try {
            obj = new JSONParser().parse(new FileReader(CONFIG_FILE));
            jo = (JSONObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Assign from config file
    private static final String jdbcDriver = (String) jo.get("jdbcDriver");
    private static final String dbHost = (String) jo.get("dbHost");
    private static final String dbName = (String) jo.get("dbName");
    private static final String dbUser =  (String) jo.get("dbUser");
    private static final String dbPassword = (String) jo.get("dbPassword");

    private static Connection conn = null;

    // Setup and return connection
    public static Connection getConnection() {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbHost + dbName, dbUser, dbPassword);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void closeConnection()  {
        try {
            if (conn != null)
                conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
