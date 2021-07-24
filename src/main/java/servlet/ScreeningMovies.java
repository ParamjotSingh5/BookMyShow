package servlet;

import DBConnection.MySQLConnectionManager;
import Helper.CommonHelper;
import Models.Movie;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import Models.Showtime;
import Models.Theater;
import Presentation.ScreeningMoviesViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet(
        name = "getScreeningMovies",
        urlPatterns = {"/getScreeningMovies"}
)

public class ScreeningMovies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println("getting Current Screening Movies");

        CommonHelper commonHelper = new CommonHelper();

        Map<Integer, Theater> ScreeningMovies = commonHelper.getScreeningMovies();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        String jsonString = gson.toJson(ScreeningMovies);
        System.out.println(jsonString);

        ServletOutputStream out = resp.getOutputStream();
        out.write(jsonString.getBytes());
        out.flush();
        out.close();
    }

}
