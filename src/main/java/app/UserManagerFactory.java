package app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;

public class UserManagerFactory {
    private static final String CONFIG_FILE = "config.json";
    private static final String CONFIG_KEY = "userManagerImpl";

    private static UserManager userManager;

    // Returns a UserManager instance based on an implementation who's filename is in a config file
    // (Uses Factory and Singleton patterns)
    public static UserManager getUserManager() {
        try {
            if(userManager == null) {
                Class<?> cl = Class.forName(getConfigFileKey());
                Constructor<?> cons = cl.getConstructor();

                userManager = (UserManager) cons.newInstance();
            }

            return userManager;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Reads config file and returns JSON object key content as a String
    private static String getConfigFileKey() throws IOException, ParseException {
        InputStream inputStream = UserManagerFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONObject jo = (JSONObject) obj;

        return jo.get(CONFIG_KEY).toString();
    }
}
