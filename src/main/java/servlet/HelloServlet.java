package servlet;

import DBConnection.MySQLConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/hello"}
    )
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("Connecting with My Sql");
        //Test My SQL Connection
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        try {
            if (conn != null) {
                Statement statement = conn.createStatement();
                if (statement != null) {
                    ResultSet resultSet = statement.executeQuery("select * from movies");
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {
                            for (int i = 1; i <= length; i++) {
                                System.out.println(meta.getColumnName(i) + ": " + resultSet.getString(meta.getColumnName(i)));
                            }
                        }
                        resultSet.close();
                    }
                    statement.close();
                }
                conn.close();
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        ServletOutputStream out = resp.getOutputStream();
        out.write("hello world ".getBytes());
        out.flush();
        out.close();
    }
    
}
