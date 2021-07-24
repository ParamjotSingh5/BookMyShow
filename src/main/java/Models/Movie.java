package Models;

import java.util.Date;

public class Movie {
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int Id;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public Date getScreeningStartDate() {
        return ScreeningStartDate;
    }

    public void setScreeningStartDate(Date screeningStartDate) {
        ScreeningStartDate = screeningStartDate;
    }

    public Date getScreeningEndDate() {
        return ScreeningEndDate;
    }

    public void setScreeningEndDate(Date screeningEndDate) {
        ScreeningEndDate = screeningEndDate;
    }

    public int getTheaterId() {
        return TheaterId;
    }

    public void setTheaterId(int theaterId) {
        TheaterId = theaterId;
    }

    private String Name;
    private String Genre;
    private Date ScreeningStartDate;
    private Date ScreeningEndDate;

    private int TheaterId;

    public Models.Showtime getShowtime() {
        return Showtime;
    }

    public void setShowtime(Models.Showtime showtime) {
        Showtime = showtime;
    }

    private Showtime Showtime;
}
