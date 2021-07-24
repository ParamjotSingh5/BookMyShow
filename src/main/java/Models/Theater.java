package Models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Theater {
    public int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSlots() {
        return Slots;
    }

    public void setSlots(int slots) {
        Slots = slots;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String Name;
    public int Slots;
    public String Location;

    public LinkedList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(LinkedList<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(Movie movie) {
        this.movies.add(movie);
    }

    public LinkedList<Movie> movies = new LinkedList<>();
}
