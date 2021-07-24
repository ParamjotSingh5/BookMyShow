package Helper;

import DBConnection.MySQLConnectionManager;
import Models.Movie;
import Models.Showtime;
import Models.Theater;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CommonHelper {

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

        try {
            if (conn != null) {
                Statement statement = conn.createStatement();
                if (statement != null) {
                    ResultSet resultSet = statement.executeQuery(sqlQuery);
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
                        resultSet.close();
                    }
                    statement.close();
                }
                conn.close();
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        return  ScreeningMovies;
    }

}
