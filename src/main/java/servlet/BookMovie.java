package servlet;

import Helper.CommonHelper;
import Models.OrderHistory;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

@WebServlet(
        name = "bookMovie",
        urlPatterns = {"/bookMovie"}
)


public class BookMovie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String _movieId = req.getParameter("movieId");
        String _selectedDate = req.getParameter("dateSelected");
        String _slotsCount = req.getParameter("slotsCount");

        System.out.println("got the parameters _movieId:" + _movieId + " _selectedDate: " + _selectedDate + " _slotsCount: " + _slotsCount);

        int movieId = Integer.parseInt(_movieId);
        int slotsCount = Integer.parseInt(_slotsCount);

        DateFormat format = new SimpleDateFormat("E MMM dd yyyy", Locale.ENGLISH);

        Date selecteddate = null;
        try {
            selecteddate = format.parse(_selectedDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        System.out.println("parsed variables Movie id: " + movieId + " and Date: " + selecteddate + " SlotsCount: " + slotsCount);

        CommonHelper commonHelper = new CommonHelper();

        int response =  commonHelper.insertBookOrder(movieId, selecteddate, slotsCount);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String jsonString = gson.toJson(response);
        System.out.println(jsonString);

        ServletOutputStream out = resp.getOutputStream();
        out.write(jsonString.getBytes());
        out.flush();
        out.close();
    }
}