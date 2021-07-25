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
        name = "addTheater",
        urlPatterns = {"/addTheater"}
)

public class AddTheater extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String _theaterName = req.getParameter("theaterName");
        String _theaterSlots = req.getParameter("theaterslots");
        String _theaterlocation = req.getParameter("theaterLocation");

        int slotsCount = Integer.parseInt(_theaterSlots);

        TheaterHelper theaterHelper = new TheaterHelper();

        int response = theaterHelper.insertTheater(_theaterName, slotsCount, _theaterlocation);

        boolean isUpdated = false;

        if(response > 0)
            isUpdated = true;

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
