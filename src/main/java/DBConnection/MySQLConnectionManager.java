package DBConnection;

import static  Helper.PropHelper.localProperties;
import java.sql.*;

public class MySQLConnectionManager {

    public Connection createNewDBConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }

        try {

            StringBuilder jdbcURL = new StringBuilder();

            jdbcURL.append(localProperties.getProperty("db.url") + "/");
            jdbcURL.append(localProperties.getProperty("db.DatabaseName") + "?");
            jdbcURL.append("user=" + localProperties.getProperty("db.user"));
            jdbcURL.append("&password=" + localProperties.getProperty("db.password"));
            jdbcURL.append("&useSSL=" + "false");

            System.out.println(jdbcURL.toString());

            Connection connection = DriverManager.getConnection(jdbcURL.toString());

            return connection;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
