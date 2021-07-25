package Presentation;

import Models.Movie;
import Models.OrderHistory;
import Models.Theater;

import java.util.LinkedList;

public class BookMovieViewModel {
    private Movie movie;
    private Theater theater;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public LinkedList<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(LinkedList<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    private LinkedList<OrderHistory> orderHistory = new LinkedList<>();
}
