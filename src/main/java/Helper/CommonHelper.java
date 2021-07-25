package Helper;

import DBConnection.MySQLConnectionManager;
import Models.Movie;
import Models.OrderHistory;
import Models.Showtime;
import Models.Theater;
import Presentation.AvailabelSlotsViewModel;
import Presentation.BookMovieViewModel;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class CommonHelper {

    public LinkedList<Theater> getALlTheatersFromDB(){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        String sqlQuery = "SELECT * FROM theatre";

        LinkedList<Theater> theaters = new LinkedList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (conn != null) {
                statement = conn.createStatement();
                if (statement != null) {
                    resultSet = statement.executeQuery(sqlQuery);
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {

                            Theater addTheater  = new Theater();

                            addTheater.setId(resultSet.getInt("idTheatre"));
                            addTheater.setName(resultSet.getString ("Name"));
                            addTheater.setSlots(resultSet.getInt("Slots"));
                            addTheater.setLocation(resultSet.getString("Location"));

                            theaters.add(addTheater);
                        }
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return theaters;
    }

    public int insertBookOrder(int movieId, Date ReservationDate, int slotsCount){

        int response = 0;
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(ReservationDate);


        String sqlorderSummary = " Insert Into OrderSummary(SlotsBooked,MovieId,ReservationDate) Values("+slotsCount+", "+movieId+", '"+strDate+"'); ";
        String sqlOrder = "INSERT INTO bookmyshow.Order(OrderDate,UserId,OrdersummaryId) VALUES(CURDATE(), 1, (SELECT LAST_INSERT_ID())); ";


        Statement statement = null;

        try {
            conn.setAutoCommit(false);
            statement = conn.createStatement();

            if (conn != null) {
                statement.addBatch(sqlorderSummary);
                statement.addBatch(sqlOrder);
                statement.executeBatch();

                //Saving the changes
                conn.commit();
                response = 1;
            }
        }
        catch (SQLException ex){
            response = -1;
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ex.printStackTrace();
        }
        finally {
            try {
                statement.close();
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

    public AvailabelSlotsViewModel getAvailabelSlots(int MovieId, Date selectedDate){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(selectedDate);

        System.out.println("parsed Date for SQL :" + strDate);

        String sqlQuery = "SELECT SUM(OS.slotsbooked) AS SlotsBooked FROM Movies M\n" +
                "INNER JOIN OrderSummary OS ON M.Idmovies = OS.MovieId\n" +
                "WHERE M.IdMovies = "+ MovieId +" AND OS.ReservationDate = ' "+ strDate +"'\n" +
                "GROUP BY OS.MovieId";

        String getSlotsByMovie = "SELECT T.Slots FROM Movies M\n" +
                "INNER JOIN Theatre T ON M.TheaterId = T.idTheatre\n" +
                "WHERE M.idMovies = "+ MovieId;


        System.out.println("get Movie Details query: " + sqlQuery);

        AvailabelSlotsViewModel slotsData = new AvailabelSlotsViewModel();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (conn != null) {
                statement = conn.createStatement();
                if (statement != null) {
                    resultSet = statement.executeQuery(sqlQuery);
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {
                            slotsData.setSlotsBooked(resultSet.getInt("SlotsBooked"));
                        }
                    }

                    resultSet = statement.executeQuery(getSlotsByMovie);
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {
                            slotsData.setAvialbaleSlots(resultSet.getInt("Slots"));
                        }
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }




        return slotsData;

    }

    public LinkedList<OrderHistory> getUserHistory(int userId){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        String sqlQuery = "SELECT M.Name, T.Name AS theaterName, T.Location, OS.SlotsBooked, OS.MovieId, OS.ReservationDate, O.OrderDate\n" +
                "FROM User U\n" +
                "INNER JOIN bookmyshow.order O ON U.IdUser = O.UserId\n" +
                "INNER JOIN OrderSummary OS ON O.OrderSummaryId = OS.IdOrderSummary\n" +
                "INNER JOIN movies M ON OS.MovieId = M.idMovies\n" +
                "INNER JOIN theatre T ON M.TheaterId = T.idTheatre\n" +
                "WHERE U.IdUser = " + userId;

        System.out.println("get Movie Details query: " + sqlQuery);

        LinkedList<OrderHistory> orderHistory = new LinkedList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (conn != null) {
                statement = conn.createStatement();
                if (statement != null) {
                    resultSet = statement.executeQuery(sqlQuery);
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {

                            OrderHistory addOrderHistory = new OrderHistory();

                            addOrderHistory.setMovieId(resultSet.getInt("MovieId"));
                            addOrderHistory.setMovieName(resultSet.getString("Name"));
                            addOrderHistory.setTheaterName(resultSet.getString("theaterName"));
                            addOrderHistory.setTheaterLocation(resultSet.getString("Location"));
                            addOrderHistory.setSlotsBooked(resultSet.getInt("SlotsBooked"));
                            addOrderHistory.setReservationDate(resultSet.getDate("ReservationDate"));
                            addOrderHistory.setOrderDateTime(resultSet.getDate("OrderDate"));


                            orderHistory.add(addOrderHistory);
                        }
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return orderHistory;

    }

    public BookMovieViewModel getMovieDetails(int MovieId){
        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        String sqlQuery = "SELECT m.idMovies, m.Name, m.Genre, m.ScreeningStartDate, m.ScreeningEndDate, \n" +
                "m.TheaterId, T.Name AS theaterName, T.Location, T.Slots, st.idshowtimes, st.Shift  FROM movies m\n" +
                "INNER JOIN showtimes st ON m.ShowtimeId = st.idshowtimes\n" +
                "INNER JOIN theatre T ON m.TheaterId = T.idTheatre\n" +
                "Where m.idMovies = " + MovieId;

        System.out.println("get Movie Details query: " + sqlQuery);

        BookMovieViewModel bookMovieViewModel = new BookMovieViewModel();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (conn != null) {
                statement = conn.createStatement();
                if (statement != null) {
                    resultSet = statement.executeQuery(sqlQuery);
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {

                            Movie addMovie = new Movie();
                            Theater addtheater = new Theater();

                            addtheater.setId(resultSet.getInt("TheaterId"));
                            addtheater.setName(resultSet.getString("theaterName"));
                            addtheater.setLocation(resultSet.getString("Location"));
                            addtheater.setSlots(resultSet.getInt("Slots"));

                            addMovie.setId(resultSet.getInt("idMovies"));
                            addMovie.setName(resultSet.getString("Name"));
                            addMovie.setGenre(resultSet.getString("Genre"));
                            addMovie.setScreeningStartDate(resultSet.getDate("ScreeningStartDate"));
                            addMovie.setScreeningEndDate(resultSet.getDate("ScreeningEndDate"));
                            addMovie.setTheaterId(resultSet.getInt("TheaterId"));

                            Showtime addShowTime = new Showtime();
                            addShowTime.setId(resultSet.getInt("idshowtimes"));
                            addShowTime.setShift(resultSet.getString("Shift"));

                            addMovie.setShowtime(addShowTime);

                            bookMovieViewModel.setTheater(addtheater);
                            bookMovieViewModel.setMovie(addMovie);
                        }
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return bookMovieViewModel;
    }

    public Map<Integer, Theater> getScreeningMovies(){

        MySQLConnectionManager mySQL = new MySQLConnectionManager();
        Connection conn = mySQL.createNewDBConnection();

        Map<Integer, Theater> ScreeningMovies = new HashMap<>();

        String sqlQuery = "SELECT T.idTheatre AS TheaterId, T.Name AS TheaterName, T.Slots, T.Location, M.idMovies AS MovieId,\n" +
                "M.Name AS MovieName, M.Genre, M.ScreeningStartDate AS 'StartDate', \n" +
                " M.ScreeningEndDate AS 'ClosingDate' , St.idshowtimes,\n" +
                " ST.Shift FROM theatre T\n" +
                "Inner join Movies M ON T.idTheatre = M.TheaterId\n" +
                "Inner join Showtimes ST ON ST.idshowtimes  = M.showtimeid\n" +
                "Where M.ScreeningEndDate >= CURDATE() AND M.ScreeningStartDate <= CURDATE()";

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (conn != null) {
                statement = conn.createStatement();
                if (statement != null) {
                    resultSet = statement.executeQuery(sqlQuery);
                    if (resultSet != null) {
                        ResultSetMetaData meta = resultSet.getMetaData();
                        int length = meta.getColumnCount();
                        while (resultSet.next()) {

                            int currentTheaterId = resultSet.getInt("TheaterId");

                            if(ScreeningMovies.containsKey(currentTheaterId)){

                                Theater addedTheater = ScreeningMovies.get(currentTheaterId);

                                Movie addMovie = new Movie();
                                addMovie.setId(resultSet.getInt("MovieId"));
                                addMovie.setName(resultSet.getString("MovieName"));
                                addMovie.setGenre(resultSet.getString("Genre"));
                                addMovie.setScreeningStartDate(resultSet.getDate ("StartDate"));
                                addMovie.setScreeningEndDate(resultSet.getDate ("ClosingDate"));
                                addMovie.setTheaterId(addedTheater.Id);

                                Showtime addShowTime = new Showtime();
                                addShowTime.setId(resultSet.getInt("idshowtimes"));
                                addShowTime.setShift(resultSet.getString("Shift"));

                                addMovie.setShowtime(addShowTime);

                                addedTheater.setMovies(addMovie);
                            }
                            else {

                                Theater addtheater = new Theater();
                                addtheater.setId(resultSet.getInt("TheaterId"));
                                addtheater.setName(resultSet.getString("TheaterName"));
                                addtheater.setSlots(resultSet.getInt("Slots"));
                                addtheater.setLocation(resultSet.getString("Location"));

                                Movie addMovie = new Movie();
                                addMovie.setId(resultSet.getInt("MovieId"));
                                addMovie.setName(resultSet.getString("MovieName"));
                                addMovie.setGenre(resultSet.getString("Genre"));
                                addMovie.setScreeningStartDate(resultSet.getDate("StartDate"));
                                addMovie.setScreeningEndDate(resultSet.getDate("ClosingDate"));
                                addMovie.setTheaterId(addtheater.Id);

                                Showtime addShowTime = new Showtime();
                                addShowTime.setId(resultSet.getInt("idshowtimes"));
                                addShowTime.setShift(resultSet.getString("Shift"));

                                addMovie.setShowtime(addShowTime);

                                addtheater.setMovies(addMovie);

                                ScreeningMovies.put(addtheater.Id, addtheater);
                            }

                        }
                    }
                }
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return  ScreeningMovies;
    }

}
