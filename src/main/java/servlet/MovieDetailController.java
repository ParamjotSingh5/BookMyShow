package servlet;

import Helper.CommonHelper;
import Models.Movie;
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
import java.util.Map;

@WebServlet(
        name = "getMovieDetails",
        urlPatterns = {"/getMovieDetails"}
)

public class MovieDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String MovieId = req.getParameter("movieId");

        System.out.println("fetching details for Movie Id: " + MovieId);

        CommonHelper commonHelper = new CommonHelper();

        BookMovieViewModel fecthedMovieDetails = new BookMovieViewModel();

        int movieid =  Integer.parseInt(MovieId);
        System.out.println("movieid: " + movieid);

        fecthedMovieDetails = commonHelper.getMovieDetails(movieid);

        //we are passing here user id as 1 (static) because we are not having any session controls in this app
        LinkedList<OrderHistory> orderHistory = commonHelper.getUserHistory(1);

        fecthedMovieDetails.setOrderHistory(orderHistory);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String jsonString = gson.toJson(fecthedMovieDetails);
        System.out.println(jsonString);

        ServletOutputStream out = resp.getOutputStream();
        out.write(jsonString.getBytes());
        out.flush();
        out.close();
    }

}
