package servlet;

import Helper.CommonHelper;
import Models.OrderHistory;
import Models.Theater;
import Presentation.BookMovieViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(
        name = "getAllTheaters",
        urlPatterns = {"/getAllTheaters"}
)
public class getAllTheaters extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("fetching All Theaters");

        CommonHelper commonHelper = new CommonHelper();

        LinkedList<Theater> allTheaters = new LinkedList<>();

        allTheaters = commonHelper.getALlTheatersFromDB();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String jsonString = gson.toJson(allTheaters);
        System.out.println(jsonString);

        ServletOutputStream out = resp.getOutputStream();
        out.write(jsonString.getBytes());
        out.flush();
        out.close();
    }
}
