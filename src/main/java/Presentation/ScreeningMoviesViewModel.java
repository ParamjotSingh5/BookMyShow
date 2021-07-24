package Presentation;

import Models.Movie;
import Models.Showtime;
import Models.Theater;

public class ScreeningMoviesViewModel {
    public Theater getTheaters() {
        return theaters;
    }

    public void setTheaters(Theater theaters) {
        this.theaters = theaters;
    }

    public Movie getMovies() {
        return movies;
    }

    public void setMovies(Movie movies) {
        this.movies = movies;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Theater theaters;
    public Movie movies;
    public Showtime showtime;
}
