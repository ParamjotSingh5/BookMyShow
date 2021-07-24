package servlet;

import DBConnection.MySQLConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        mySQL.createNewDBConnection();

        ServletOutputStream out = resp.getOutputStream();
        out.write("hello world ".getBytes());
        out.flush();
        out.close();
    }
    
}
