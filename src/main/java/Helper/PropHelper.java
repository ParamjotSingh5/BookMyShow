package Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropHelper {

    public static Properties localProperties = new Properties();

    static {
        getLocalProperties();
    }

    public static void getLocalProperties() {

        try (InputStream input = new FileInputStream("src/main/java/configs/config.properties")) {
            // load a properties file
            localProperties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
