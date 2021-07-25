package Models;

import java.util.Date;

public class OrderHistory {
    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getTheaterName() {
        return TheaterName;
    }

    public void setTheaterName(String theaterName) {
        TheaterName = theaterName;
    }

    public String getTheaterLocation() {
        return TheaterLocation;
    }

    public void setTheaterLocation(String theaterLocation) {
        TheaterLocation = theaterLocation;
    }

    public int getSlotsBooked() {
        return SlotsBooked;
    }

    public void setSlotsBooked(int slotsBooked) {
        SlotsBooked = slotsBooked;
    }

    public int getMovieId() {
        return MovieId;
    }

    public void setMovieId(int movieId) {
        MovieId = movieId;
    }

    public Date getReservationDate() {
        return ReservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        ReservationDate = reservationDate;
    }

    public Date getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    private String MovieName;
    private String TheaterName;
    private String TheaterLocation;
    private int SlotsBooked;
    private int MovieId;
    private Date ReservationDate;
    private Date OrderDateTime;
}
