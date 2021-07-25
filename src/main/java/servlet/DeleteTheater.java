package servlet;

import Helper.TheaterHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "deleteTheater",
        urlPatterns = {"/deleteTheater"}
)

public class DeleteTheater extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String _theaterId = req.getParameter("theaterId");

        int theaterId = Integer.parseInt(_theaterId);

        boolean isUpdated = false;

        TheaterHelper theaterHelper = new TheaterHelper();

        boolean isAnyMovieBeingScreened = theaterHelper.isAnyMovieScreened(theaterId);
        boolean isAnyMovieBookedForNextDay = theaterHelper.isAnyMovieBookedForNextDay(theaterId);

        if(isAnyMovieBookedForNextDay || isAnyMovieBeingScreened)
        {
            // No deletion
            isUpdated = false;
        }
        else
        {
            theaterHelper.DeleteTheater(theaterId);
            isUpdated = true;
        }

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String jsonString = gson.toJson(isUpdated);
        System.out.println(jsonString);

        ServletOutputStream out = resp.getOutputStream();
        out.write(jsonString.getBytes());
        out.flush();
        out.close();
    }
}