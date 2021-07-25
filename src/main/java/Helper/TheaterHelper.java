package Helper;

import DBConnection.MySQLConnectionManager;
import servlet.DeleteTheater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheaterHelper {

    public boolean isAnyMovieBookedForNextDay(int theaterId){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        boolean response = true;

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            ps = conn.prepareStatement("SELECT COUNT(M.idMovies) as moviesCount FROM theatre T\n" +
                    "Inner join Movies M ON T.idTheatre = M.TheaterId\n" +
                    "INNER JOIN OrderSummary OS ON M.idMovies = OS.MovieId\n" +
                    "WHERE T.idTheatre = ? AND OS.ReservationDate = DATE_ADD(CURDATE(), INTERVAL 1 DAY)\n" +
                    "Group BY T.idTheatre;");
            ps.setInt(1, theaterId);


            resultSet = ps.executeQuery();
            int count = 0;

            while (resultSet.next()) {
                count = resultSet.getInt("moviesCount");
                System.out.println("movies booked for next day count: " + count);
            }

            if (count == 0){
                //response = DeleteTheater(theaterId);
                response = false;
            }

            return response;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return response;
    }

    public boolean isAnyMovieScreened(int theaterId){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        boolean response = true;

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            ps = conn.prepareStatement("SELECT COUNT(M.idMovies) as moviesCount FROM theatre T\n" +
                    "Inner join Movies M ON T.idTheatre = M.TheaterId\n" +
                    "Where M.ScreeningEndDate > CURDATE() AND M.ScreeningStartDate <= CURDATE()\n" +
                    "AND T.idTheatre = ? \n" +
                    "Group BY T.idTheatre;");
            ps.setInt(1, theaterId);


            resultSet = ps.executeQuery();
            int count = 0;

            while (resultSet.next()) {
                count = resultSet.getInt("moviesCount");
                System.out.println("screened movie count: " + count);
            }

            if (count == 0){
                //response = DeleteTheater(theaterId);
                response = false;
            }

            return response;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return response;
    }

    public int DeleteTheater(int theaterId){

        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement("DELETE FROM theatre T Where T.idTheatre = ?");
            ps.setInt(1, theaterId);

            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    public int insertTheater(String Name, int slots, String location){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();


        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement("INSERT INTO theatre(Name, Slots, Location) VALUES(?,?,?)");
            ps.setString(1, Name);
            ps.setInt(2, slots);
            ps.setString(3, location);

            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    public int updateTheater(int id, String Name, int slots, String location){

        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();


        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement("Update theatre T Set T.Name = ?, T.Slots = ?, T.Location = ? WHERE T.idTheatre = ?");
            ps.setString(1, Name);
            ps.setInt(2, slots);
            ps.setString(3, location);
            ps.setInt(4, id);

            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }
}
