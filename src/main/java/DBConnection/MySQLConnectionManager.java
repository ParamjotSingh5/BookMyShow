package DBConnection;

import static  Helper.PropHelper.localProperties;
import java.sql.*;

public class MySQLConnectionManager {

    public void createNewDBConnection(){

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
            if (connection != null) {
                Statement statement = connection.createStatement();
                if (statement != null) {
                    ResultSet resultSet = statement.executeQuery("select * from movies");
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while(resultSet.next())
                        {
                            for(int i = 1; i <= length; i++){
                                System.out.println(meta.getColumnName(i) + ": " + resultSet.getString(meta.getColumnName(i)));
                            }
                        }
                        resultSet.close();
                    }
                    statement.close();
                }
                connection.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
