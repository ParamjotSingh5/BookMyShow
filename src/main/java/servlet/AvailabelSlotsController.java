package servlet;

import Helper.CommonHelper;
import Models.OrderHistory;
import Presentation.AvailabelSlotsViewModel;
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
        name = "getAvailableSlotsByDate",
        urlPatterns = {"/getAvailableSlotsByDate"}
)

public class AvailabelSlotsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String selectedDate = req.getParameter("selectedDate");
        String currentMovieId = req.getParameter("movieId");

        System.out.println("fetching Available Slots Data for Movie id: "+currentMovieId + "and Date: "+selectedDate);

        int movieid =  Integer.parseInt(currentMovieId);

        DateFormat format = new SimpleDateFormat("E MMM dd yyyy", Locale.ENGLISH);

        Date date = null;
        try{
            date = format.parse(selectedDate);
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }

        System.out.println("parsed variables Movie id: "+movieid + "and Date: "+date);

        CommonHelper commonHelper = new CommonHelper();

        AvailabelSlotsViewModel slotsViewModel = commonHelper.getAvailabelSlots(movieid, date);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String jsonString = gson.toJson(slotsViewModel);
        System.out.println(jsonString);

        ServletOutputStream out = resp.getOutputStream();
        out.write(jsonString.getBytes());
        out.flush();
        out.close();
    }
}
